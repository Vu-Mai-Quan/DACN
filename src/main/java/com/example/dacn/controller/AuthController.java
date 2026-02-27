package com.example.dacn.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dacn.service.NguoiDungService;
import com.example.dacn.service.NguoiDungService.LoginDto;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;


@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

	NguoiDungService dungService;
	
	@GetMapping("login")
	public ResponseEntity<?> login(@org.springframework.web.bind.annotation.RequestBody LoginDto param) {
		try {
			return ResponseEntity.ok().body(dungService.login(param));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e);
		}
	}
	
}
