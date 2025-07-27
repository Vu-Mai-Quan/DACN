/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.service;

import java.security.Key;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author ADMIN
 */
public interface IJwtService {

    String createToken(UserDetails details);
   
    UserDetails kiemTraTaiKhoanTrongToken(String token);
    
}
