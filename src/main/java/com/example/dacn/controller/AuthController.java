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
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
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

    @PostMapping("client/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto dto, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(BaseResponse.builder()
                    .data(authService.login(dto))
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e) {
            StringBuilder builder = new StringBuilder();
            switch (e.getMessage()) {
                case "User is disabled" -> builder.append("Tài khoản chưa kích hoạt, vui lòng xem tin nhắn ở Email để kích hoạt tài khoản");
                case "User account is locked" -> builder.append("Tài khoản đã bị khóa");
                default -> builder.append(e.getMessage());
            }
            return AuthController.ErrorResponseBuilder(HttpStatus.CONFLICT, builder.toString(), request.getRequestURI());

        }

    }

    @PostMapping("client/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto dto, HttpServletRequest request) {
        try {
            String out = authService.dangKiTaiKhoan(dto);
            return ResponseEntity.ok(BaseResponse.builder()
                    .data(out)
                    .status(HttpStatus.OK)
                    .build());
        } catch (EntityExistsException | MessagingException e) {
            return AuthController.ErrorResponseBuilder(HttpStatus.CONFLICT, e.getMessage(), request.getRequestURI());
        }
    }

    public static ResponseEntity<?> ErrorResponseBuilder(HttpStatus status, String message, String url) {
        return ResponseEntity.status(status).body(
                ErrorResponse.builder()
                        .message(message)
                        .status(status)
                        .url(url)
                        .build());
    }

    @PutMapping("{role}/phan-quyen")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<?> phanQuyenNguoiDung(HttpServletRequest request, @RequestBody Set<PhanQuyenRq> phanQuyenRq,
                                                @PathVariable("role") String roleParam) {
        try {
            switch (roleParam.toLowerCase()) {
                case "admin" -> System.out.println("admin");
                case "manager" -> System.out.println("manager");
            }
            return ResponseEntity.ok(BaseResponse.builder()
                    .data(phanQuyenRq.isEmpty() ? Set.of() : authService.phanQuyenTaiKhoan(phanQuyenRq))
                    .status(HttpStatus.OK)
                    .build());
        } catch (EntityExistsException e) {
            return AuthController.ErrorResponseBuilder(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI());
        }
    }


    @GetMapping("xac-thuc")
    protected void confirmAccount(@RequestParam UUID id, @RequestParam String token,
                                  HttpServletResponse response) throws IOException {
        try {
            authService.kiemTraTokenDangKi(new IdRegisterToken(id, token));
            response.sendRedirect("https://www.google.com/search?q=gg+d%E1%BB%8Bch&oq=&gs_lcrp=EgZjaHJvbWUqBggBEEUYOzIOCAAQRRgnGDkYgAQYigUyBggBEEUYOzIGCAIQRRg7MgYIAxBFGDsyDAgEEAAYQxiABBiKBTINCAUQABiDARixAxiABDIGCAYQRRg8MgYIBxBFGDzSAQg0NzYxajBqN6gCALACAA&sourceid=chrome&ie=UTF-8");
        } catch (MessagingException | IOException e) {
            response.sendRedirect("https://www.google.com");
        }
    }


    public record RefreshTokenRq(@JsonProperty("token_refresh") String token) {
    }

    @GetMapping("client/refresh-token")
    protected ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRq refreshTokenRq, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(BaseResponse.builder()
                    .status(HttpStatus.OK)
                    .data(Map.of("token", authService.taoMoiTokenBangRefresh(refreshTokenRq.token)))
                    .build());
        } catch (Exception e) {
            return AuthController.ErrorResponseBuilder(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI());
        }
    }

    protected void logout(){}
}
