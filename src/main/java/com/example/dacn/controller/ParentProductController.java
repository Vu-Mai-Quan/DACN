package com.example.dacn.controller;


import com.example.dacn.basetemplate.ErrorResponse;
import com.example.dacn.basetemplate.dto.response.BaseResponse;
import com.example.dacn.db1.model.DanhMucSanPham;
import com.example.dacn.db1.model.HangSanXuat;
import com.example.dacn.service.IParentProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/parent/")
@RequiredArgsConstructor
public class ParentProductController {

    private final IParentProductService<DanhMucSanPham> danhMuc;
    private final IParentProductService<HangSanXuat> hangSx;

    @PostMapping(value = "danh-muc")
    protected ResponseEntity<?> taoHoacCapNhat(@RequestParam(defaultValue = "null") UUID id, @RequestBody DanhMucSanPham danhMuc, HttpServletRequest request) {
        return taoChaSanPham(id, danhMuc, request);
    }

    @PostMapping(value = "hang-san-xuat")
    protected ResponseEntity<?> taoHoacCapNhat(@RequestParam(defaultValue = "null") UUID id, @RequestBody HangSanXuat danhMuc, HttpServletRequest request) {
        return taoChaSanPham(id, danhMuc, request);
    }

    private <T> ResponseEntity<?> taoChaSanPham(UUID id, T dauVao, HttpServletRequest request) {
        try {
            String message = "";
            if (dauVao != null) {
                if (dauVao instanceof DanhMucSanPham) {
                    message = this.danhMuc.createOrUpdate(id, (DanhMucSanPham) dauVao);
                } else if (dauVao instanceof HangSanXuat) {
                    message = this.hangSx.createOrUpdate(id, (HangSanXuat) dauVao);
                }
                return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.builder()
                        .data(message)
                        .status(HttpStatus.CREATED)
                        .build());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder()
                        .message("Đối tượng truyền vào rỗng")
                        .status(HttpStatus.BAD_REQUEST)
                        .url(request.getRequestURI())
                        .build());
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ErrorResponse.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .url(request.getRequestURI())
                    .build());
        }

    }

}
