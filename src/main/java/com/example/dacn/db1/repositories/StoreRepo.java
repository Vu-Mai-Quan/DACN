/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.example.dacn.db1.repositories;

import com.example.dacn.db1.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ADMIN
 */
public interface StoreRepo extends JpaRepository<Store, Short> {

   boolean existsByName(String name);
}
