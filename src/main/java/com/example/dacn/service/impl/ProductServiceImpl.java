/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.service.impl;

import com.example.dacn.config.BearerAuthenticationToken;
import com.example.dacn.db1.model.ImageProduct;
import com.example.dacn.db1.model.Product;
import com.example.dacn.db1.repositories.NguoiDungRepo;
import com.example.dacn.db1.repositories.ProductRepo;
import com.example.dacn.db1.repositories.StoreRepo;
import com.example.dacn.mapper.ProductMapper;
import com.example.dacn.service.ImageService;
import com.example.dacn.service.ProductService;
import com.example.dacn.template.dto.ProductDto;
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.*;

import static com.example.dacn.template.enumModel.StoreStatus.DISABLED;

/**
 *
 * @author ADMIN
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    ProductRepo productRepo;
    ProductMapper productMapper;
    StoreRepo storeRepo;
    NguoiDungRepo nguoiDungRepo;
    ImageService imageService;

    @Override
    @Transactional("db1TrManager")
    public ProductMapper.ProductResponse createProduct(ProductDto productDto, Map<Long, String> file) {
        BearerAuthenticationToken user
                = (BearerAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        var store = storeRepo.findById(productDto.getIdStore());

        if (store.isEmpty() || store.get().getStatus().equals(
                DISABLED)) {
            return null;
        }

        var createBy = nguoiDungRepo.getReferenceById(UUID.fromString(user.getCredentials().toString()));

        Product product = Product.builder().createBy(createBy)
                .store(store.get())
                .sku(generateSku(productDto.getName(), store.get().getId()))
                .build();
        product.setCreateBy(createBy);
        product.setStore(store.get());
        product.setSku(generateSku(productDto.getName(), store.get().getId()));
        List<ImageProduct> setImage = file == null ? List.of() : file.entrySet().stream().map(item -> {
            var id = ImageProduct.ProductImageId.builder()
                    .imageId(item.getKey())
                    .build();
            return new ImageProduct(id, false, item.getValue(), product);
        }).toList();
        product.setImages(setImage);
        product.setImageUrl(setImage.isEmpty() ? null : (setImage.getFirst()).getUrlImage());
        Product p = productMapper.productDtoToProduct(product, productDto);
        try {
            return productMapper.productToProductResponse(productRepo.save(p));
        }catch (Exception e){
            if( file != null && !file.isEmpty()){
                imageService.removeAllById(file.keySet());
            };
            return null;
        }
    }

    private final Random RANDOM = new Random();

    private String generateSku(String name, short idStore) {
        // 1. NN - store id (2 digits)
        String storePart = String.format("%02d", idStore);

        // 2. AAA - từ tên (3 chữ cái)
        String normalized = normalize(name);
        String letters = normalized.replaceAll("[^a-zA-Z]", "");

        String namePart = letters.length() >= 3
                ? letters.substring(0, 3).toUpperCase(Locale.ROOT)
                : String.format("%-3s", letters).replace(' ', 'X').toUpperCase();

        // 3. aa - random lowercase
        String randomPart = randomLowercase();

        return storePart + "-" + namePart + "-" + randomPart;
    }

    private String normalize(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", ""); // bỏ dấu tiếng Việt
    }

    private String randomLowercase() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            sb.append(alphabet.charAt(RANDOM.nextInt(alphabet.length())));
        }
        return sb.toString();
    }

    @Override
    public boolean updateProduct(ProductDto productDto, UUID id, Map<UUID, String> file) {
        boolean isSuccess = false;
        var store = storeRepo.findById(productDto.getIdStore());
        var productOp = productRepo.findById(id);
        if (store.isEmpty() || store.get().getStatus().equals(
                DISABLED) || productOp.isEmpty()) {
            return isSuccess;
        }
        Product product = productMapper.productDtoToProduct(productOp.get(), productDto);
        product.setStore(store.get());
        productRepo.save(product);
        return !isSuccess;
    }

    @Override
    public boolean disableProduct(UUID id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

//    @Override
//    public Page<ProductInfo> getAllProduct(Pageable pageable, Map<String, Object> filter) {
//        return this.productRepo.findAllProduct(pageable);
//    }

    @Override
    public ProductMapper.ProductDetailResponse getProductById(UUID id) {
//    	return productRepo.findById(id).orElse(null);
        var p = productRepo.getDetailProductById(id);
        return p.map(productMapper::productToProductDetailResponse).orElse(null);

    }

    public ProductMapper.ProductDetailResponse getProductByName(String name) {
        return productRepo.findByName(name).map(productMapper::productToProductDetailResponse).orElse(null);
    }
}
