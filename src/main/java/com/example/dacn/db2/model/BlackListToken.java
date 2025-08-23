/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.db2.model;

import com.example.dacn.basetemplate.BaseEntity;
import com.example.dacn.db2.model.compositekey.IdBlackListToken;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author ADMIN
 */
@Table(name = "black_list_token", schema = "main", indexes = {@Index(columnList = "token", name = "idx_token_black_list")})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlackListToken {
    @Id
    IdBlackListToken idBlackListToken;
    @Column(name = "thoi_han")
    LocalDateTime thoiHan;
    @Column(name = "da_bi_khoa")
    boolean daBiKhoa;


    @PostLoad
    public void setDaBiKhoa() {
        this.daBiKhoa = thoiHan.isBefore(LocalDateTime.now());
    }
}
