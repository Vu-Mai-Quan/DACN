package com.example.dacn.db1.repositories;

import com.example.dacn.db1.model.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NhanVienRepository extends JpaRepository<NhanVien, UUID> {
}