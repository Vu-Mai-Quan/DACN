/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.service.impl;

import com.example.dacn.config.BearerAuthenticationToken;
import com.example.dacn.db1.model.Product;
import com.example.dacn.db1.model.Store;
import com.example.dacn.db1.repositories.NguoiDungRepo;
import com.example.dacn.db1.repositories.ProductRepo;
import com.example.dacn.db1.repositories.StoreRepo;
import com.example.dacn.mapper.ProductMapper;
import com.example.dacn.service.ProductService;
import com.example.dacn.template.dto.ProductDto;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
    public boolean createProduct(List<ProductDto> productDto) {
        BearerAuthenticationToken user
                = (BearerAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        var createBy = nguoiDungRepo.findByName(user.getName());
        if (createBy.isEmpty()) {
            return false;
        }
        Set<Short> idStore = productDto.stream().map(ProductDto::getIdStore).collect(
                Collectors.toSet());
        Map<Short, Store> storeMap
                = storeRepo.findAllById(idStore)
                        .stream()
                        .collect(Collectors.toMap(Store::getId,
                                Function.identity()));
        List<Product> products = new ArrayList<>();
        
        Set<String> setImagePath = new HashSet<>();
        try {
            for (var item : productDto) {
                if (storeMap.get(item.getIdStore()) == null) {
                    return false;
                }
                String fileName = UUID.randomUUID() + "_" + item.getFile().getOriginalFilename();
                String path = "uploads/" + fileName;
                Path filePath = Paths.get(path);
                Files.copy(item.getFile().getInputStream(), filePath);
                Product product = productMapper.productDtoToProduct(item);
                product.setStore(storeMap.get(item.getIdStore()));
                setImagePath.add(path);
                product.setImageUrl(path);
                products.add(product);
            }
         
        } catch (IOException e) {
            CompletableFuture.runAsync(() -> {
                for (var path : setImagePath) {
                    try {
                        Files.deleteIfExists(Paths.get(path));
                    } catch (IOException ignored) {
                    }
                }
            });
            throw new RuntimeException("ảnh bị lỗi", e);
        }

        productRepo.saveAll(products);
        return true;
    }

    private boolean createAsyncProduct(
            Function<AtomicReference<Map<String, MultipartFile>>, Boolean> create,
            Consumer<Map<String, MultipartFile>> consumer) {
        AtomicReference<Map<String, MultipartFile>> atomicReference = new AtomicReference<>();
        boolean isTrue = create.apply(atomicReference);
        if (isTrue) {
            consumer.accept(atomicReference.get());
        }
        return isTrue;
    }

    private void uploadImageProduct(Map<String, MultipartFile> map) {
        Set<Path> set = new HashSet<>();
        for (var item : map.entrySet()) {
            Path path = Paths.get(item.getKey());
            try {
                Files.copy(item.getValue().getInputStream(), path);
                set.add(path);
            } catch (IOException ex) {
                try {
                    for (var i : set) {
                        Files.deleteIfExists(i);
                    }

                } catch (IOException ex1) {
                    System.getLogger(ProductServiceImpl.class.getName()).log(
                            System.Logger.Level.ERROR,
                            (String) null, ex1);
                }
                throw new RuntimeException("images không hợp lệ", ex);
            }

        }
    }

    @Override
    public boolean updateProduct(ProductDto productDto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean disableProduct(UUID id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
