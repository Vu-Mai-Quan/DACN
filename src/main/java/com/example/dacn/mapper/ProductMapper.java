/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.dacn.mapper;

import com.example.dacn.db1.model.Product;
import com.example.dacn.template.dto.ProductDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ADMIN
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "imageUrl",
            expression = "java(getUrl(productDto.getFile()))")
    @Mapping(target = "store", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "quantity", source = "productDto.quantity")
    Product productDtoToProduct(ProductDto productDto);


    default String getUrl(MultipartFile multipartFile) {

        return multipartFile.getOriginalFilename();
    }
}
