/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.mapper;

import com.example.dacn.basetemplate.dto.response.TaiKhoanResponese;
import com.example.dacn.db1.model.TaiKhoan;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 *
 * @author ADMIN
 */
@Component(value = "ttndTKRoleMapper")
@RequiredArgsConstructor
public class ThongTinNDVaTaiKhoanMapper implements IMapperService {

    private final ModelMapper mapper;
    private final Map<String, Set<String>> rolesMap = Map.of("1", Set.of("ROLE_CLIENT"),
            "2", Set.of("ROLE_CUSTOMER", "ROLE_CLIENT"),
            "3", Set.of("ROLE_CUSTOMER", "ROLE_CLIENT", "ROLE_MANAGER"),
            "4", Set.of("ROLE_CUSTOMER", "ROLE_CLIENT", "ROLE_MANAGER", "ROLE_ADMIN")
    );

    @Override
    public <S, D> D mapperObject(S source, Class<D> out, Consumer<ModelMapper> consumer) {
        consumer.accept(mapper);
        if (source == null) {
            return null;
        }
        return mapper.map(source, out);
    }

    @Override
    public <S, D> D mapperObject(S source, Class<D> out) {

        mapper.typeMap(TaiKhoan.class, TaiKhoanResponese.class)
                .addMappings((type) -> {
                    type.map(
                            tk -> {
                                var i = String.valueOf(1);
                                System.out.println(i);
                                return rolesMap.containsKey(i) ? rolesMap.get(i) : rolesMap.get("1");
                            },
                            TaiKhoanResponese::setDanhSachChucVu);
                    type.map(TaiKhoan::getUsername, TaiKhoanResponese::setEmail);
                    type.map(TaiKhoan::getType, TaiKhoanResponese::setType);
                    type.map(TaiKhoan::isAccountNonLocked, TaiKhoanResponese::setCoBiKhoa);
                    type.map(TaiKhoan::isEnabled, TaiKhoanResponese::setDaKichHoat);
                });
//        mapper.typeMap(ThongTinNguoiDung.class, ThongTinNDResponse.class)
//                .addMapping(ThongTinNguoiDung::getTaiKhoan, ThongTinNDResponse::setTaiKhoan);
        if (source == null) {
            return null;
        }
        return mapper.map(source, out);
    }

}
