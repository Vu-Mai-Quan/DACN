/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.db1.repositories;

import com.example.dacn.db1.model.Role;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author ADMIN
 */
public interface ChucVuRepo extends JpaRepository<Role, UUID>{
    @Query(value = "select r.role_name from role r", nativeQuery = true)
    Set<String> timHetTenChucVu();
    @Query("select r from Role r WHERE r.role IN ('ADMIN', 'CUSTOMER','CLIENT','MANAGER')")
    Set<Role> findAllInName();
}
