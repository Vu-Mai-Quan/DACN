/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.example.dacn.db1.repositories;

import com.example.dacn.db1.model.OrderItem;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author ADMIN
 */
public interface OrderItemRepo extends CrudRepository<OrderItem, Long> {
    
}
