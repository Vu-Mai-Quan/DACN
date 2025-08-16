/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.controller;

import com.example.dacn.basetemplate.ErrorResponse;
import com.example.dacn.basetemplate.dto.response.BaseResponse;
import com.example.dacn.basetemplate.dto.response.ThongTinNDResponse;
import com.example.dacn.service.INguoiDungService;
import jakarta.persistence.NoResultException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ADMIN
 */
@RestController
@RequestMapping(value = "/nguoi-dung/")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NguoiDungController {

    INguoiDungService dungService;

    @GetMapping("/danh-sach")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN','ROLE_MANAGER')")
    protected ResponseEntity<?> layDSNguoiDung(
            @RequestParam(value = "search", required = false) String keyWord,
            @RequestParam(value = "order", required = false) String order
            , @RequestParam(defaultValue = "1") int page
            , @RequestParam(defaultValue = "5") int size) {
        size = size > 100 ? 100 : size;
        Sort.Order order1 = new Sort.Order(Sort.Direction.DESC, "id");
        if (StringUtils.hasLength(order)) {
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(order);
            if (matcher.find()) {
                String columnName = matcher.group(1);
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    order1 = new Sort.Order(Sort.Direction.ASC, columnName);
                } else {
                    order1 = new Sort.Order(Sort.Direction.DESC, columnName);
                }
            }
        }
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order1));
        return ResponseEntity.ok(BaseResponse.builder()
                .status(HttpStatus.OK)
                .data(dungService.layDanhSachTTNguoiDung(keyWord, order, pageable))
                .build());
    }

    @GetMapping("{id}")
    protected ResponseEntity<Object> getOne(@PathVariable("id") UUID id) {
        try {
            return ResponseEntity.ok(dungService.getOne(id));
        } catch (NoResultException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .data(null)
                    .url("api/v1/nguoi-dung/" + id)
                    .message("Không tìm thấy user id: %s".formatted(id))
                    .build());
        }
    }
}
