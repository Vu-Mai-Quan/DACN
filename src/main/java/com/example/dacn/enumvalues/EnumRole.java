/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.enumvalues;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author ADMIN
 */
@RequiredArgsConstructor
@Getter
public enum EnumRole {
    ADMIN("Quản trị viên"),CLIENT("Khách hàng"),CUSTOMER("Nhân viên"),MANAGER("Quản lí");
    private final String desciptions;
}
