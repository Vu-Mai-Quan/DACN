/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.dacn.service;

import com.example.dacn.template.dto.ProductDto;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author ADMIN
 */
public interface ProductService {
   boolean createProduct(List<ProductDto> productDto);
   
   boolean updateProduct(ProductDto productDto);
   
   boolean disableProduct(UUID id);
}
