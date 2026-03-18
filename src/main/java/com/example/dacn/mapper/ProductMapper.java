/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.dacn.mapper;

import com.example.dacn.db1.model.Product;
import com.example.dacn.template.dto.ProductDto;
import com.example.dacn.template.enumModel.ProductStatus;
import lombok.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.util.UUID;

/**
 *
 * @author ADMIN
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {


    @Mapping(target = "sku", ignore = true)
    @Mapping(target = "store", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    Product productDtoToProduct(@MappingTarget Product product, ProductDto productDto);

    ProductResponse productToProductResponse(Product product);

    @Builder
    record ProductResponse(UUID id, String imageUrl, String name, BigDecimal price, int quantity,
                           ProductStatus status) {
    }
}
