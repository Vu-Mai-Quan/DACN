/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.service;

import com.example.dacn.basetemplate.dto.request.LoginDto;
import com.example.dacn.basetemplate.dto.response.LoginResponse;

/**
 *
 * @author ADMIN
 */
public interface IAuthService {
    LoginResponse login(LoginDto loginDto);
}
