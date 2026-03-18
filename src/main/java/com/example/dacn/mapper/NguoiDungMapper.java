package com.example.dacn.mapper;

import com.example.dacn.db1.model.viewmodel.NguoiDungView;
import com.example.dacn.template.dto.NguoiDungResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface NguoiDungMapper {

    @Mapping(expression = "java(buildResNd(nd))", target = "roles")
    @Mapping(target = "storeName", source = "nd.storeName")
    @Mapping(target = "isActive", expression = "java(nd.isEnabled())")
    @Mapping(target = "id", expression = "java(nd.getId())")
    NguoiDungResponse toResponse(NguoiDungView nd);


    default Collection<? extends GrantedAuthority> buildResNd(NguoiDungView nd) {
        return nd.getAuthorities();
    }
}
