/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.controller;

import com.example.dacn.basetemplate.ErrorResponse;
import com.example.dacn.basetemplate.dto.request.LoginDto;
import com.example.dacn.basetemplate.dto.request.RegisterDto;
import com.example.dacn.basetemplate.dto.response.BaseResponse;
import com.example.dacn.service.IAuthService;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ADMIN
 */
@RestController()
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/client/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto dto, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(BaseResponse.builder()
                    .data(authService.login(dto))
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ErrorResponse.builder()
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .url(request.getRequestURI())
                            .build()
            );
        }

    }

    @PostMapping("/client/register")
    protected ResponseEntity<?> register(@Valid @RequestBody RegisterDto dto, HttpServletRequest request) {
        try {
            String out = authService.dangKiTaiKhoan(dto);
            return ResponseEntity.ok(BaseResponse.builder()
                    .data(out)
                    .status(HttpStatus.OK)
                    .build());
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ErrorResponse.builder()
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .url(request.getRequestURI())
                            .build());
        }
    }
}
