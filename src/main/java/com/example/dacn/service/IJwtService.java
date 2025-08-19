/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.service;

import com.example.dacn.db1.model.TaiKhoan;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Map;

/**
 *
 * @author ADMIN
 */
public interface IJwtService {

    String createToken(Map<String, Object> claims, UserDetails userDetails);
   
    UserDetails kiemTraTaiKhoanTrongToken(String token);
    
    String createRefreshToken(TaiKhoan details);
    
    boolean isRefreshToken(String token);
    
    Date exprired(String token);
}
