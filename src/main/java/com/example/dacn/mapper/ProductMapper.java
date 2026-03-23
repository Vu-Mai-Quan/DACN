/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.dacn.mapper;

import com.example.dacn.db1.model.ImageProduct;
import com.example.dacn.db1.model.Product;
import com.example.dacn.template.dto.ProductDto;
import com.example.dacn.template.enumModel.ProductStatus;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author ADMIN
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {


    @Mapping(target = "images", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "sku", ignore = true)
    @Mapping(target = "store", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    Product productDtoToProduct(@MappingTarget Product product, ProductDto productDto);

    ProductResponse productToProductResponse(Product product);

    //    @Mapping(target = "images", source = "product.images")
    @Mapping(target = "productResponse", source = "product")
    @Mapping(target = "imageProductList", source = "product.images")
    ProductDetailResponse productToProductDetailResponse(Product product);

    @Builder
    record ProductResponse(UUID id, String imageUrl, String name, BigDecimal price, int quantity,
                           ProductStatus status) implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;
    }

    //, Set<ImageProduct> images
    record ProductDetailResponse(@JsonUnwrapped ProductResponse productResponse,
                                 Set<ImageProduct> imageProductList) implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;
    }

}
