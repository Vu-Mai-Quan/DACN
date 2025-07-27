/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.enumvalues;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author ADMIN
 */
@RequiredArgsConstructor
public enum EnumTypeAccount {
    CLIENT("Khách hàng"), CUSTOMER("Nhân viên");

    private final String moTa;

    public String getMoTa() {
        return this.moTa;
    }

}
