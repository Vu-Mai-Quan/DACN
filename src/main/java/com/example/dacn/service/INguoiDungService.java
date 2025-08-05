/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.service;

import com.example.dacn.basetemplate.dto.response.ThongTinNDResponse;
import com.example.dacn.db1.model.viewmodel.ThongTinNdVaChucVu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author ADMIN
 */
public interface INguoiDungService {
//    Page<ThongTinNDResponse> layDanhSachTTNguoiDung(Pageable pageable);
//    Page<ThongTinNdVaChucVu> layDanhSachTTNguoiDung(Pageable pageable);
    Page<ThongTinNDResponse> layDanhSachTTNguoiDung(Pageable pageable);
}
