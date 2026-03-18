/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.dacn.service;

import com.example.dacn.mapper.ProductMapper;
import com.example.dacn.template.dto.ProductDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ADMIN
 */
public interface ProductService {
    ProductMapper.ProductResponse createProduct();

    boolean updateProduct(ProductDto productDto);

    boolean disableProduct(UUID id);

    List<ProductMapper.ProductResponse> getAllProduct(Pageable pageable, Map<String, Object> filter);


}
