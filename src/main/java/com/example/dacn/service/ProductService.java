/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.dacn.service;

import com.example.dacn.db1.model.Product;
import com.example.dacn.db1.repositories.projections.ProductInfo;
import com.example.dacn.mapper.ProductMapper;
import com.example.dacn.template.dto.ProductDto;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ADMIN
 */
public interface ProductService {
    ProductMapper.ProductResponse createProduct(ProductDto productDto, Map<Long, String> filePath);

    boolean updateProduct(ProductDto productDto, UUID id, Map<UUID, String> file);

    boolean disableProduct(UUID id);

//    Page<ProductInfo> getAllProduct(Pageable pageable, Map<String, Object> filter);

    ProductMapper.ProductDetailResponse getProductById(UUID id);

    Object getProductByName(String name);
}
