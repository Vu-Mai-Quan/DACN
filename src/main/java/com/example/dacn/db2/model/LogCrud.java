/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.db2.model;

import com.example.dacn.db2.model.compositekey.IdLogCrud;
import com.example.dacn.enumvalues.EnumBehavior;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.sql.Date;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "lich_su_tuong_tac")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@IdClass(value = IdLogCrud.class)
public class LogCrud {
    
    @Column(name = "id_nguoi_dung")
    @Id
    UUID idNguoiDung;
    
    @Column(name = "hanh_vi", nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    EnumBehavior hanhVi;
    
    @Column(name = "id_tuong_tac")
    @Id
    UUID idTuongTac;
    
    @Column(name = "bang_tuong_tac", length = 30, nullable = false)
    String bangTuongTac;
    
    @Column(name = "create_at")
    @Id
    @Setter(value = AccessLevel.NONE)
    Date createAt = new Date(System.currentTimeMillis());
}
