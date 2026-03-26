package com.example.dacn.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.dacn.db1.model.NguoiDung;
import com.example.dacn.db1.repositories.NguoiDungRepo;
import com.example.dacn.service.JwtService;
import com.example.dacn.service.JwtService.ParamJwt;
import com.example.dacn.service.JwtService.TypeToken;
import com.example.dacn.service.NguoiDungService;
import com.example.dacn.template.dto.NguoiDungDto;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class NguoiDungServiceImpl implements NguoiDungService {

    NguoiDungRepo ndRepo;
    AuthenticationManager authenticationManager;
    JwtService<JwtService.ParamJwt<NguoiDung>> jwtServiceImpl;

    @Override
    public boolean createNguoiDung(NguoiDungDto dung) {
        ndRepo.findAll();
        return false;
    }

    @Override
    public LoginResponse login(LoginDto login) {
        var au = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        login.username(),
                        login.password()));
        var nd = NguoiDung.class.cast(au.getPrincipal());

        return LoginResponse.builder()
                .refreshToken(
                        jwtServiceImpl.createJwt(new ParamJwt<NguoiDung>(nd,
                                TypeToken.REFRESH)))
                .accessToken(jwtServiceImpl.createJwt(new ParamJwt<NguoiDung>(nd,
                        TypeToken.ACCESS)))
                .build();
    }

    @Override
    public Page<NguoiDung> readAllNd(Pageable pageable,
            NguoiDungViewParamSearch dungSearchParam) {
        
        return ndRepo.findAll(pageable);
    }

   
}
