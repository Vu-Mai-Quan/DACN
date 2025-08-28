/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.mapper;

import com.example.dacn.basetemplate.dto.response.TaiKhoanResponese;
import com.example.dacn.basetemplate.dto.response.ThongTinNDResponse;
import com.example.dacn.db1.model.viewmodel.ThongTinNdVaChucVu;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * @author ADMIN
 */

@Component(value = "ttndTKRoleMapper")
@RequiredArgsConstructor
public class ThongTinNDVaTaiKhoanMapper implements IMapperService {

    private final ModelMapper mapper;
    private final ObjectMapper objectCodec;
  

    @Override
    public <S, D> D mapperObject(S source, Class<D> out, Consumer<ModelMapper> consumer) {
        consumer.accept(mapper);
        return source != null ? mapper.map(source, out) : null;
    }

    @Override
    public <S, D> D mapperObject(S source, Class<D> out) {
        // Luôn luôn tạo TypeMap một lần và sử dụng lại.
        // Nếu bạn tạo nó trong mỗi lần gọi hàm, nó sẽ làm chậm ứng dụng.
        TypeMap<ThongTinNdVaChucVu, ThongTinNDResponse> typeMap = mapper.getTypeMap(ThongTinNdVaChucVu.class, ThongTinNDResponse.class);
        if (typeMap == null) {
            typeMap = mapper.createTypeMap(ThongTinNdVaChucVu.class, ThongTinNDResponse.class);
            typeMap.addMappings(mapping -> {
                mapping
                        .using(ctx -> {
                            String taiKhoanJson = (String) ctx.getSource();
                            try {
                                return objectCodec.readValue(taiKhoanJson, TaiKhoanResponese.class);
                            } catch (IOException e) {
                                throw new RuntimeException("Lỗi mapping TaiKhoanResponese", e);
                            }
                        })
                        .map(ThongTinNdVaChucVu::getTaiKhoan, ThongTinNDResponse::setTaiKhoan);
                mapping.map(ThongTinNdVaChucVu::getId, ThongTinNDResponse::setIdNguoiDung);
            });

        }
        return source != null ? mapper.map(source, out) : null;
    }

}
