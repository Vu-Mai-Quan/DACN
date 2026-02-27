package com.example.dacn.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.example.dacn.db1.model.NguoiDung;

public interface JwtService {
	String createJwt(ParamJwt jwt);

	Collection<? extends GrantedAuthority> getRoles(String jwt);

	public static record ParamJwt(NguoiDung nguoiDung, TypeToken type) {

	}

	public static enum TypeToken {
		REFRESH, ACCESS;
	}
}
