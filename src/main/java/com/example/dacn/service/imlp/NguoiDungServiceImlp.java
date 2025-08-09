/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.service.imlp;

import com.example.dacn.basetemplate.dto.response.ThongTinNDResponse;
import com.example.dacn.db1.repositories.ThongTinNDRepo;
import com.example.dacn.mapper.IMapperService;
import com.example.dacn.service.INguoiDungService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author ADMIN
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NguoiDungServiceImlp implements INguoiDungService {

    public NguoiDungServiceImlp(
            @Qualifier("ttndTKRoleMapper") IMapperService ims, ThongTinNDRepo dRepo
    ) {
        this.ims = ims;
        this.dRepo = dRepo;
    }

    IMapperService ims;
    ThongTinNDRepo dRepo;

    @Override
    public Page<ThongTinNDResponse> layDanhSachTTNguoiDung(Pageable pageable) {
        return dRepo.layDanhSachNguoiDung(pageable)
                .map(item-> ims.mapperObject(item, ThongTinNDResponse.class));
    }


}
