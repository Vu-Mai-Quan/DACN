package com.example.dacn.db1.model;

import com.example.dacn.basetemplate.BaseEntityUpdateAt;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "danh_muc_san_pham", schema = "dacn_repair_service_booking_system", indexes = {@Index(columnList = "ten_danh_muc")})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DanhMucSanPham extends BaseEntityUpdateAt {
    @Column(name = "ten_danh_muc", nullable = false, unique = true, columnDefinition = "nvarchar(100)")
    String tenDanhMuc;
    @Column(name = "da_kich_hoat")
    boolean daKichHoat;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "danh_muc_id", referencedColumnName = "id")
    DanhMucSanPham danhMucSanPham;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "danhMucSanPham")
    Set<DanhMucSanPham> danhMucSanPhams = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "danhMucSanPham")
    Set<SanPham> sanPhams = new HashSet<>();
}
