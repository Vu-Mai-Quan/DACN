/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.db1.model;

import com.example.dacn.db1.model.compositekey.IdRefreshToken;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author ADMIN
 */
@Table(name = "refresh_token")
@IdClass(IdRefreshToken.class)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefreshToken {

    @ManyToOne(fetch = FetchType.EAGER)
    @Id
    @MapsId("idNguoiDung")
    @JoinColumn(name = "id_nguoi_dung", referencedColumnName = "id", nullable = false)
    private TaiKhoan idNguoiDung;
    @Id
    @Column(nullable = false)
    private long exprired;
    
    @Column(length = 300)
    String token;
}
