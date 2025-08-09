package com.example.dacn.db1.model;

import com.example.dacn.basetemplate.BaseEntityUpdateAt;
import com.example.dacn.enumvalues.EnumLoaiKhachHang;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "khach_hang", schema = "dacn_repair_service_booking_system")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KhachHang extends BaseEntityUpdateAt {

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @MapsId
    @JoinColumn(name = "id", unique = true)
    ThongTinNguoiDung thongTinNguoiDung;
    @Column(name = "loai_khach_hang", nullable = false)
    @Enumerated(EnumType.STRING)
    EnumLoaiKhachHang loaiKhachHang;

    @Column(name = "so_don_dat")
    @ColumnDefault(value = "0")
    @Min(0)
    int soDonDat;

    @Column(name = "tong_chi_tieu")
    @ColumnDefault(value = "0")
    @Min(0)
    long tongChiTieu;

}
