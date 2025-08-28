/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.db2.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * @author ADMIN
 */
@Table(name = "black_list_token", schema = "main", indexes = {@Index(columnList = "id_user, token", name = "idx_token_black_list")})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlackListToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @Column(name = "id_user")
    UUID idUser;
    @Column(length = 300)
    String token;
    @Column(name = "da_bi_khoa")
    boolean daBiKhoa;

    public BlackListToken(UUID idUser, String token, boolean daBiKhoa) {
        this.idUser = idUser;
        this.token = token;
        this.daBiKhoa = daBiKhoa;
    }
}
