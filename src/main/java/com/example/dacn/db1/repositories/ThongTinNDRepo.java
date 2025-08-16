/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.db1.repositories;

import com.example.dacn.db1.model.ThongTinNguoiDung;
import com.example.dacn.db1.model.viewmodel.ThongTinNdVaChucVu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

/**
 * @author ADMIN
 */
public interface ThongTinNDRepo extends JpaRepository<ThongTinNguoiDung, UUID> {

    @Query("select t from ThongTinNguoiDung t WHERE t.sdt = :sdt")
    Optional<ThongTinNguoiDung> findBySdt(@Param("sdt") String sdt);

    @Query(value="select count(t.sdt) from thong_tin_nguoi_dung t WHERE t.sdt = :sdt limit 1", nativeQuery=true)
    int kiemTraSdtTonTai(@Param("sdt") String sdt);

    @Query(value = "select u from ThongTinNdVaChucVu u")
    Page<ThongTinNdVaChucVu> layDanhSachNguoiDung(Pageable page);
    @Query(value = """
            select u from ThongTinNdVaChucVu u where u.hoTen like lower(concat('%',?1,'%')) or u.sdt like lower(concat('%',?1,'%'))
            or json_extract(u.taiKhoan, '$.email') like lower(concat('%',?1,'%'))
            """)
    Page<ThongTinNdVaChucVu> timNguoiDungTheoKeyword(String keyword,
                                                     Pageable pageable);

    @Query(value = "select u from ThongTinNdVaChucVu u where u.id = :id")
    Optional<ThongTinNdVaChucVu> layNguoiDungBangId(UUID id);


}
