package com.example.dacn.db1.repositories;

import com.example.dacn.db1.model.DanhMucSanPham;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DanhMucSanPhamRepository extends JpaRepository<DanhMucSanPham, UUID> {
}