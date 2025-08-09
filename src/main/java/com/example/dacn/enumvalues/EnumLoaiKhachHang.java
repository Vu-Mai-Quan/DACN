package com.example.dacn.enumvalues;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EnumLoaiKhachHang {
    DONG("Đồng"),BAC("Bạc"),VANG("Vàng");

    private final String moTa;
}
