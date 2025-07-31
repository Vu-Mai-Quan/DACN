/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.service;

import com.example.dacn.basetemplate.dto.response.ThongTinNDResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author ADMIN
 */
public interface INguoiDungService {
    Page<ThongTinNDResponse> layDanhSachTTNguoiDung(Pageable pageable);
}
