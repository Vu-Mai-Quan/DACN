/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.db1.repositories;

import com.example.dacn.db1.model.RefreshToken;
import com.example.dacn.db1.model.compositekey.IdRefreshToken;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ADMIN
 */
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, IdRefreshToken> {

    @Query(value = "select rft.token from refresh_token rft where rft.id_nguoi_dung = :id order by rft.exprired desc limit 1", nativeQuery = true)
    Optional<String> getNewToken(@Param("id") UUID id);
}
