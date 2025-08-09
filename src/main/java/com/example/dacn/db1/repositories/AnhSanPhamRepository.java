package com.example.dacn.db1.repositories;

import com.example.dacn.db1.model.AnhSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AnhSanPhamRepository extends JpaRepository<AnhSanPham, Integer>, JpaSpecificationExecutor<AnhSanPham> {
}