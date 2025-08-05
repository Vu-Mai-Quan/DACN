/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.db1.repositories;

import com.example.dacn.db1.model.TaiKhoan;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ADMIN
 */
public interface TaiKhoanRepo extends JpaRepository<TaiKhoan, UUID> {

    @Query(value = "select COUNT(*) from tai_khoan tk WHERE tk.email = :email limit 1", nativeQuery = true)
    int kiemTraEmailDaTonTai(@Param("email") String email);

    @Query("SELECT tk from TaiKhoan tk JOIN FETCH tk.roles where tk.email = :email")
    Optional<TaiKhoan> timTaiKhoanTheoEmail(@Param("email") String email);

}
