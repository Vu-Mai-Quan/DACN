/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.template.dto;

import com.example.dacn.template.enumModel.ProductStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

/**
 *
 * @author ADMIN
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
public final class ProductDto {


    String name;

    int quantity;

    short idStore;

    BigDecimal price;

    ProductStatus status;
}
