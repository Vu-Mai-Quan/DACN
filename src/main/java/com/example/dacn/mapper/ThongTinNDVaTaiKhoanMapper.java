/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.mapper;

import com.example.dacn.basetemplate.dto.response.TaiKhoanResponese;
import com.example.dacn.basetemplate.dto.response.ThongTinNDResponse;
import com.example.dacn.db1.model.TaiKhoan;
import com.example.dacn.db1.model.ThongTinNguoiDung;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author ADMIN
 */
@Component(value = "ttndTKRoleMapper")
@Scope(value = "SCOPE_SINGLETON")
@RequiredArgsConstructor
public class ThongTinNDVaTaiKhoanMapper {

    private final ModelMapper mapper;

    public ThongTinNDResponse toNDResponse(ThongTinNguoiDung nguoiDung) {
        mapper.typeMap(TaiKhoan.class, TaiKhoanResponese.class)
                .addMappings((type) -> {
                    type.map(
                            tk -> tk.getAuthorities().stream().map(map -> map.getAuthority()).collect(Collectors.toSet()),
                            TaiKhoanResponese::setDanhSachChucVu);
                });
        return mapper.map(nguoiDung, ThongTinNDResponse.class);
    }

}
