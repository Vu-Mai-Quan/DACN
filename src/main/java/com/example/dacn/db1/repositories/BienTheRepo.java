package com.example.dacn.db1.repositories;

import com.example.dacn.db1.model.BienTheSanPham;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BienTheRepo extends JpaRepository<BienTheSanPham, UUID> {
}
