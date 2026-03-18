/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.example.dacn.db1.repositories;

import com.example.dacn.db1.model.Product;

import java.util.Optional;
import java.util.UUID;

import com.example.dacn.db1.repositories.projections.ProductInfo;
import io.micrometer.core.lang.NonNullApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

/**
 *
 * @author ADMIN
 */
public interface ProductRepo extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    Optional<ProductInfo> findBySku(String sku);

    Optional<ProductInfo> findByName(String name);

    @NonNull
    @Query("""
            select p.id, p.name, p.imageUrl, p.price, u.username, s.name, p.sku, p.status, p.quantity
            from Product p join p.store s join p.createBy u
            """)
    Page<ProductInfo> findAllProduct(@NonNull Pageable pageable);

//    Page<Product> findAll(Pageable pageable, Specification<Product> specification);
}
