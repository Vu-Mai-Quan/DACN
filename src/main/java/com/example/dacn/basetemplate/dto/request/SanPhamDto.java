package com.example.dacn.basetemplate.dto.request;

import com.example.dacn.db1.model.SanPham;
import com.example.dacn.enumvalues.EnumProductStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link SanPham}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class SanPhamDto implements Serializable {
    UUID id;
    @JsonProperty(value = "ten_sp")
    String tenSanPham;
    @JsonProperty(value = "mo_ta")
    String moTa;
    @JsonProperty(value = "gia_ca")
    BigDecimal giaCa;
    @JsonProperty(value = "trang_thai")
    EnumProductStatus trangThai;
    @JsonProperty(value = "danh_muc_id")
    UUID danhMucSanPhamId;
    @JsonProperty(value = "nhan_vien_id")
    UUID nhanVienId;
    @JsonProperty(value = "hang_san_xuat")
    UUID hangSanXuatId;
    @JsonProperty(value = "avatar")
    String anhChinh;
    @JsonProperty(value = "danh_sach_anh")
    Set<AnhSanPhamDto> anhSanPhams;
}