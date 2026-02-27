package com.example.dacn.mapper;

import java.util.Collection;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.GrantedAuthority;

import com.example.dacn.db1.model.NguoiDung;
//import com.example.dacn.template.dto.NguoiDungResponse;

@Mapper(componentModel = "spring")
public interface NguoiDungMapper {
	@Mapping(expression = "java(buidResNd(nd))", target = "roles")
	@Mapping(target = "storeName", source = "nd.store.name")
	@Mapping(target = "isActive", expression = "java(nd.isEnabled())")
	@Mapping(target = "id", expression = "java(nd.getId())")
	NguoiDungResponse toResponse(NguoiDung nd);

	public static record NguoiDungResponse(UUID id ,String username, String storeName,
			Collection<? extends GrantedAuthority> roles, boolean isActive) {

	}

	default Collection<? extends GrantedAuthority> buidResNd(NguoiDung nd) {
		return nd.getAuthorities();
	}
}
