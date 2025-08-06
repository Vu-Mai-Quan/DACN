/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.db2.repositories;

import com.example.dacn.db2.model.BlackListToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

/**
 *
 * @author ADMIN
 */
public interface BlackListTokenRepo extends JpaRepository<BlackListToken, UUID> {

    @Query(value="select COUNT(*) from black_list_token btk WHERE btk.token = :token limit 1", nativeQuery = true)
    int findByToken(@Param("token") String token);
}
