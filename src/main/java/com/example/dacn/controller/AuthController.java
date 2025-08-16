/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.controller;

import com.example.dacn.basetemplate.ErrorResponse;
import com.example.dacn.basetemplate.dto.request.LoginDto;
import com.example.dacn.basetemplate.dto.request.PhanQuyenRq;
import com.example.dacn.basetemplate.dto.request.RegisterDto;
import com.example.dacn.basetemplate.dto.response.BaseResponse;
import com.example.dacn.service.IAuthService;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto dto, HttpServletRequest request) {
        try {
            String out = authService.dangKiTaiKhoan(dto);
            return ResponseEntity.ok(BaseResponse.builder()
                    .data(out)
                    .status(HttpStatus.OK)
                    .build());
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    ErrorResponse.builder()
                            .message(e.getMessage())
                            .status(HttpStatus.CONFLICT)
                            .url(request.getRequestURI())
                            .build());
        }
    }


    @PutMapping("{role}/phan-quyen")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> phanQuyenNguoiDung(HttpServletRequest request, @RequestBody Set<PhanQuyenRq> phanQuyenRq, @PathVariable("role") String roleParam) {
        try {
            switch (roleParam.toLowerCase()) {
                case "admin":
                    System.out.println("admin");
                    break;
                case "manager":
                    System.out.println("manager");
                    break;
            }
            return ResponseEntity.ok(BaseResponse.builder()
                    .data(phanQuyenRq.isEmpty() ? Set.of() : authService.phanQuyenTaiKhoan(phanQuyenRq))
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
