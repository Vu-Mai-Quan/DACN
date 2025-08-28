/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.db2.model;

import com.example.dacn.enumvalues.EnumBehavior;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

/**
 * @author ADMIN
 */
@Entity
@Table(name = "lich_su_tuong_tac")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class LogCrud {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "id_nguoi_dung")
    UUID idNguoiDung;

    @Column(name = "id_tuong_tac")
    UUID idTuongTac;

    @Setter(value = AccessLevel.NONE)
    @Column(name = "create_at", updatable = false, nullable = false)
    @Getter(value = AccessLevel.NONE)
    long createAt;

    public LocalDateTime getCreateAt() {
        Instant instant = Instant.now();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public void setCreateAt() {
        this.createAt = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    @Column(name = "hanh_vi", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    EnumBehavior hanhVi;


    @Column(name = "bang_tuong_tac", length = 30, nullable = false)
    String bangTuongTac;

    @Column(name = "noi_dung", length = 300)
    String noiDung;


}
