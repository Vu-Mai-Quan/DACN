/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.example.dacn.controller;

import com.example.dacn.service.NguoiDungService;

import static lombok.AccessLevel.PRIVATE;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ADMIN
 */
@RestController
@RequestMapping("/nguoi-dung/")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class NguoiDungController {

    NguoiDungService nguoiDungService;

    @GetMapping("get-all")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    ResponseEntity<?> getAllNguoiDung(
            @PageableDefault() Pageable pageable,
            @ModelAttribute NguoiDungService.NguoiDungViewParamSearch dungViewParamSearch) {
        return ResponseEntity.ok(nguoiDungService.readAllNd(pageable,
                dungViewParamSearch));
    }

    @GetMapping("info")
    @PreAuthorize("authentication.name =='admin@gmail.com'")
    ResponseEntity<?> getInfoUser() {
        return ResponseEntity.ok("Xin chào Admin!");
    }
}
