package com.example.dacn.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.dacn.db1.model.viewmodel.NguoiDungView;
import com.example.dacn.db1.repositories.NguoiDungRepo;
import com.example.dacn.service.JwtService;
import com.example.dacn.service.JwtService.ParamJwt;
import com.example.dacn.service.JwtService.TypeToken;
import com.example.dacn.service.NguoiDungService;
import com.example.dacn.template.dto.NguoiDungDto;
import com.example.dacn.template.dto.NguoiDungResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class NguoiDungServiceImpl implements NguoiDungService {

    NguoiDungRepo ndRepo;
    AuthenticationManager authenticationManager;
    JwtService<JwtService.ParamJwt> jwtServiceImpl;

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
        var nd = NguoiDungView.class.cast(au.getPrincipal());

        var ndR = NguoiDungResponse.builder().id(nd.getId())
                .username(nd.getUsername()).isActive(nd.isEnabled())
                .roles(nd.getAuthorities()).build();

        return LoginResponse.builder()
                .refreshToken(
                        jwtServiceImpl.createJwt(new ParamJwt(nd,
                                TypeToken.REFRESH)))
                .accessToken(jwtServiceImpl.createJwt(new ParamJwt(nd,
                        TypeToken.ACCESS)))
                .nguoiDung(ndR).build();
    }

}
