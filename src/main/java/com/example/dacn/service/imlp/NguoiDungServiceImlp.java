/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.service.imlp;

import com.example.dacn.basetemplate.dto.response.ThongTinNDResponse;
import com.example.dacn.db1.model.viewmodel.ThongTinNdVaChucVu;
import com.example.dacn.db1.repositories.ThongTinNDRepo;
import com.example.dacn.mapper.IMapperService;
import com.example.dacn.service.INguoiDungService;
import jakarta.persistence.NoResultException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @author ADMIN
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@RequiredArgsConstructor
public class NguoiDungServiceImlp implements INguoiDungService {


    IMapperService ims;
    final ThongTinNDRepo dRepo;

    @Autowired
    public void qualifierSetter(@Qualifier("ttndTKRoleMapper") IMapperService ims) {
        this.ims = ims;
    }

    @Override
    public Page<ThongTinNDResponse> layDanhSachTTNguoiDung(String keyWord, String order, Pageable pageable) {
//        Thực ra chỉ cần truyền vào '' là lấy hết dữ liệu
        return StringUtils.hasLength(keyWord) ? mappingRp(dRepo.timNguoiDungTheoKeyword(keyWord, pageable))
                : mappingRp(dRepo.layDanhSachNguoiDung(pageable));

    }

    private Page<ThongTinNDResponse> mappingRp(Page<ThongTinNdVaChucVu> page) {
        return page.map(item -> ims.mapperObject(item, ThongTinNDResponse.class));
    }

    @Override
    @PostAuthorize("returnObject.taiKhoan.email.equals(authentication.name) || hasRole('ROLE_ADMIN')")
    public ThongTinNDResponse getOne(UUID id) {
        var rp = dRepo.layNguoiDungBangId(id).orElseThrow(NoResultException::new);
        return ims.mapperObject(rp, ThongTinNDResponse.class);
    }


}
