package com.example.dacn.db1.repositories;

import com.example.dacn.db1.model.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SanPhamRepo extends JpaRepository<SanPham, UUID> {
}
