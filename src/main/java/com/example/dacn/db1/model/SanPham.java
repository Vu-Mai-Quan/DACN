package com.example.dacn.db1.model;


import com.example.dacn.basetemplate.BaseEntityUpdateAt;
import com.example.dacn.enumvalues.EnumProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "san_pham", schema = "dacn_repair_service_booking_system", indexes = {@Index(columnList = "ten_san_pham")})

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SanPham extends BaseEntityUpdateAt {
    @Column(name = "ten_san_pham", unique = true, nullable = false, columnDefinition = "nvarchar(200)")
    String tenSanPham;
    @Column(name = "mo_ta", nullable = false, columnDefinition = "TEXT")
    String moTa;
    @Column(name = "gia_ca", nullable = false)
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal giaCa;

    @Min(value = 0)
    @Column(name = "tong_so_luong", nullable = false)
    int tongSoLuong;

    @Column(name = "trang_thai", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    EnumProductStatus trangThai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_danh_muc", referencedColumnName = "id")
    DanhMucSanPham danhMucSanPham;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nhan_vien", referencedColumnName = "id")
    NhanVien nhanVien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hang_san_xuat", referencedColumnName = "id")
    HangSanXuat hangSanXuat;

    @Column(name = "anh_chinh", length = 300)
    String anhChinh;

    @OneToMany(mappedBy = "sanPham", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    Set<AnhSanPham> anhSanPhams;
}
