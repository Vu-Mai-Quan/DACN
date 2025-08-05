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

    @Query(name = "ThongTinNdVaChucVu.findAll")
    Page<ThongTinNdVaChucVu> layDanhSachNguoiDung(Pageable page);


}
