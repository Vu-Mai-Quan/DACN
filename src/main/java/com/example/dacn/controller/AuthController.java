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
import com.example.dacn.db2.model.compositekey.IdRegisterToken;
import com.example.dacn.service.IAuthService;
import com.fasterxml.jackson.annotation.JacksonInject;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.InjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

/**
 * @author ADMIN
 */
@RestController()
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/client/login")
    @JacksonInject
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto dto, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(BaseResponse.builder()
                    .data(authService.login(dto))
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e) {
            StringBuilder builder = new StringBuilder();
            switch (e.getMessage()) {
                case "User is disabled":
                    builder.append("Tài khoản chưa kích hoạt, vui lòng xem tin nhắn ở Email để kích hoạt tài khoản");
                    break;
                case "User account is locked":
                    builder.append("Tài khoản đã bị khóa");
                    break;
                default:
                    builder.append(e.getMessage());
                    break;
            }
            return ResponseEntity.badRequest().body(
                    ErrorResponse.builder()
                            .message(builder.toString())
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
        } catch (EntityExistsException | MessagingException e) {
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
    public ResponseEntity<?> phanQuyenNguoiDung(HttpServletRequest request, @RequestBody Set<PhanQuyenRq> phanQuyenRq,
                                                @PathVariable("role") String roleParam) {
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


    @GetMapping("xac-thuc")
    protected void confirmAccount(@RequestParam UUID id, @RequestParam String token,
                                  HttpServletResponse response) throws IOException {
        try {
            authService.kiemTraTokenDangKi(new IdRegisterToken(id, token));
            response.sendRedirect("https://www.google.com/search?q=gg+d%E1%BB%8Bch&oq=&gs_lcrp=EgZjaHJvbWUqBggBEEUYOzIOCAAQRRgnGDkYgAQYigUyBggBEEUYOzIGCAIQRRg7MgYIAxBFGDsyDAgEEAAYQxiABBiKBTINCAUQABiDARixAxiABDIGCAYQRRg8MgYIBxBFGDzSAQg0NzYxajBqN6gCALACAA&sourceid=chrome&ie=UTF-8");
        } catch (Exception e) {
            response.sendRedirect("https://www.google.com");
        }
    }
}
