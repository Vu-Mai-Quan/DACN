/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.template.dto;

import com.example.dacn.template.enumModel.ProductStatus;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ADMIN
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Getter
@Builder
public class ProductDto {

    String name, sku;

    UUID createByNV;

    int quantity;

    MultipartFile file;

    short idStore;

    BigDecimal price;
    ProductStatus status;
}
