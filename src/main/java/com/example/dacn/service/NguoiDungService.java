package com.example.dacn.service;

import com.example.dacn.template.dto.NguoiDungDto;
import com.example.dacn.template.dto.NguoiDungResponse;

import lombok.Builder;

public interface NguoiDungService  {

	boolean createNguoiDung(NguoiDungDto dung);

	LoginResponse login(LoginDto login);

	public static record LoginDto(String username, String password) {

	}

	@Builder
	public static record LoginResponse(String refreshToken, String accessToken,
			NguoiDungResponse nguoiDung) {

	}
}
