package com.example.dacn.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.GrantedAuthority;

import com.example.dacn.db1.model.NguoiDung;
import com.example.dacn.template.dto.NguoiDungResponse;

@Mapper(componentModel = "spring")
public interface NguoiDungMapper {

    @Mapping(expression = "java(buildResNd(nd))", target = "roles")
    @Mapping(target = "storeName", source = "nd.store.storeName")
    @Mapping(target = "isActive", expression = "java(nd.isEnabled())")
    @Mapping(target = "id", expression = "java(nd.getId())")
    NguoiDungResponse toResponse(NguoiDung nd);


    default Collection<? extends GrantedAuthority> buildResNd(NguoiDung nd) {
        return nd.getAuthorities();
    }
}
