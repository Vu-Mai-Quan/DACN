/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.controller;

import com.example.dacn.basetemplate.dto.response.ThongTinNDResponse;
import com.example.dacn.service.INguoiDungService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ADMIN
 */
@RestController
@RequestMapping(value = "/nguoi-dung/")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NguoiDungController {

    INguoiDungService dungService;

    @GetMapping("danh-sach")
    protected ResponseEntity<Page<ThongTinNDResponse>> layDSNguoiDung(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        size = size > 100 ? 100 : size;
        return ResponseEntity.ok(dungService.layDanhSachTTNguoiDung(PageRequest.of(page, size)));
    }
}
