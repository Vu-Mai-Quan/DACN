package com.example.dacn.db1.repositories;

import com.example.dacn.db1.model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface KhachHangRepository extends JpaRepository<KhachHang, UUID> {
}