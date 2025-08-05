/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.db1.model;

import com.example.dacn.basetemplate.BaseEntityUpdateAt;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

/**
 * @author ADMIN

 */
@Table(name = "thong_tin_nguoi_dung", schema = "dacn_repair_service_booking_system")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NamedEntityGraphs({
        @NamedEntityGraph(name = "ThongTinNguoiDung.JoinRole",
                attributeNodes = @NamedAttributeNode(value = "taiKhoan", subgraph = "TaiKhoan.Roles"),
                subgraphs = {
                        @NamedSubgraph(name = "TaiKhoan.Roles", attributeNodes = {
                                @NamedAttributeNode(value = "roles")
                        })})
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "ThongTinNguoiDung.selectAllWithRole", query = """
                select * from thong_tin_nd_va_chuc_vu
                """,resultSetMapping = "mappingsThongTinNdWithRole")
})

public class ThongTinNguoiDung extends BaseEntityUpdateAt {

    @Column(length = 13, nullable = false, unique = true)
    String sdt;
    @Column(name = "ho_ten", length = 50)
    String hoTen;
    @Column(name = "ngay_sinh")
    Date ngaySinh;
    @Column(length = 225)
    String avatar;
    @OneToOne(mappedBy = "thongTinNguoiDung",
            cascade = CascadeType.ALL, orphanRemoval = true)
    TaiKhoan taiKhoan;
}
