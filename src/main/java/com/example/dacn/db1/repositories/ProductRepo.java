/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.example.dacn.db1.repositories;

import com.example.dacn.db1.model.Product;
import com.example.dacn.db1.repositories.projections.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author ADMIN
 */
public interface ProductRepo extends JpaRepository<Product, UUID> {
    Optional<ProductInfo> findBySku(String sku);

    @EntityGraph(value = "Product.images")
    Optional<Product> findByName(String name);


    @NonNull
//    @Query("""
//            select p.id as id, p.name as name, p.imageUrl as imageUrl, p.price as price, u.username as username,\s
//            s.storeName as storeName,\s
//            p.sku as sku, p.status as status,\s
//            p.quantity as quantity
//            from Product p join p.store s join p.createBy u
//           \s""")
    @Query("from Product p")
    Page<ProductInfo> findAllProduct(@NonNull Pageable pageable);

    @EntityGraph(value = "Product.detail")
    @Query("select p from Product p where p.id = ?1")
    Optional<Product> getDetailProductById(UUID id);


    //    @EntityGraph(value = "Product.images")
    @Override
    @NonNull
    Optional<Product> findById(@NonNull UUID id);
//    Page<Product> findAll(Pageable pageable, Specification<Product> specification);


}
