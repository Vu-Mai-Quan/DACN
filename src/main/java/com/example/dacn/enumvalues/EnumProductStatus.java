package com.example.dacn.enumvalues;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EnumProductStatus {
    NGUNG_BAN("Ngừng bán"), HET_HANG("Hết hàng"), DANG_NHAP_HANG("Đang nhập hàng"), SAP_TOI("Sắp mở bán");

    private final String moTa;
}
