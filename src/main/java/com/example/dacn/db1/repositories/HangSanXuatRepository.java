package com.example.dacn.db1.repositories;

import com.example.dacn.db1.model.HangSanXuat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HangSanXuatRepository extends JpaRepository<HangSanXuat, UUID> {
}