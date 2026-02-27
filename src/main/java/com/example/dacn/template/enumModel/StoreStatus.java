/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.example.dacn.template.enumModel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author ADMIN
 */
@RequiredArgsConstructor
@Getter
public enum StoreStatus {
    ACTIVE("Kích hoạt"), DISABLED("Đã ẩn");
    private final String descriptions;

}
