/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.service.impl;

import com.example.dacn.config.BearerAuthenticationToken;
import com.example.dacn.db1.model.Product;
import com.example.dacn.db1.repositories.NguoiDungRepo;
import com.example.dacn.db1.repositories.ProductRepo;
import com.example.dacn.db1.repositories.StoreRepo;
import com.example.dacn.mapper.ProductMapper;
import com.example.dacn.service.ProductService;
import com.example.dacn.template.dto.ProductDto;

import static com.example.dacn.template.enumModel.StoreStatus.DISABLED;
import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.example.dacn.template.enumModel.ProductStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RegExUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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


    @Override
    @Transactional("db1TrManager")
    public ProductMapper.ProductResponse createProduct() {
        BearerAuthenticationToken user
                = (BearerAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        var createBy = nguoiDungRepo.getReferenceById(UUID.fromString(user.getCredentials().toString()));
        Product product = Product.builder()
                .createBy(createBy)
                .status(ProductStatus.CREATING)
                .build();
        return productMapper.productToProductResponse(productRepo.save(product));

    }

    @Override
    public boolean updateProduct(ProductDto productDto) {
        boolean isSuccess = false;
        var store = storeRepo.findById(productDto.getIdStore());
        var productOp = productRepo.findById(productDto.getId());
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

    @Override
    public List<ProductMapper.ProductResponse> getAllProduct(Pageable pageable, Map<String, Object> filter) {
        return List.of();
    }


}
