package com.example.dacn.mapper;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.GrantedAuthority;

import com.example.dacn.db1.model.viewmodel.NguoiDungView;

@Mapper(componentModel = "spring")
public interface NguoiDungMapper {

    @Mapping(expression = "java(buidResNd(nd))", target = "roles")
    @Mapping(target = "storeName", source = "nd.storeName")
    @Mapping(target = "isActive", expression = "java(nd.isEnabled())")
    @Mapping(target = "id", expression = "java(nd.getId())")
    com.example.dacn.template.dto.NguoiDungResponse toResponse(NguoiDungView nd);

//	public static record NguoiDungResponse(UUID id ,String username, String storeName,
//			Collection<? extends GrantedAuthority> roles, boolean isActive) {
//
//	}
    default Collection<? extends GrantedAuthority> buidResNd(NguoiDungView nd) {
        return nd.getAuthorities();
    }
}
