package com.example.dacn.db1.model;

import com.example.dacn.basetemplate.BaseEntityUpdateAt;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "nhan_vien", schema = "dacn_repair_service_booking_system")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NhanVien extends BaseEntityUpdateAt {

    @OneToOne(orphanRemoval = true)
    @MapsId
    @JoinColumn(name = "id", unique = true)
    ThongTinNguoiDung thongTinNguoiDung;
    @Column(name = "doanh_so_ca_nhan")
    BigDecimal doanSoCaNhan;
    @Column(name = "so_don_ban")
    int soDonBan;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_quan_li", referencedColumnName = "id")
    NhanVien quanLi;

    @OneToMany(mappedBy = "quanLi", fetch = FetchType.LAZY)
    Set<NhanVien> nhanVienSet = new HashSet<>();

    @OneToMany(mappedBy = "nhanVien", fetch = FetchType.LAZY)
    Set<SanPham> sanPhamTao;
    @Column(name = "luong_co_ban", nullable = false)
    @Min(0)
    long luongCoBan;
    @Column(name = "ghi_chu", columnDefinition = "TEXT")
    String ghiChu;
}
