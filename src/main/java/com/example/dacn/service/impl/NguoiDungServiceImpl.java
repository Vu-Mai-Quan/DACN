package com.example.dacn.service.impl;

import com.example.dacn.db1.model.viewmodel.NguoiDungView;
import com.example.dacn.db1.repositories.NguoiDungRepo;
import com.example.dacn.service.JwtService;
import com.example.dacn.service.JwtService.ParamJwt;
import com.example.dacn.service.JwtService.TypeToken;
import com.example.dacn.service.NguoiDungService;
import com.example.dacn.template.dto.NguoiDungDto;
import com.example.dacn.ultil.NdViewSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

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

        return LoginResponse.builder()
                .refreshToken(
                        jwtServiceImpl.createJwt(new ParamJwt(nd,
                                TypeToken.REFRESH)))
                .accessToken(jwtServiceImpl.createJwt(new ParamJwt(nd,
                        TypeToken.ACCESS)))
                .build();
    }

    @Override
    public Page<NguoiDungView> readAllNd(Pageable pageable,
            NguoiDungViewParamSearch dungSearchParam) {
        Specification<NguoiDungView> buildQuery = Specification.where(null);
        if (dungSearchParam != null) {
            if (stringIsValid(dungSearchParam.username())) {
                buildQuery.and(NdViewSpecification.searchParam(
                        dungSearchParam.username().trim()));
            }
            if (stringIsValid(dungSearchParam.store())) {
                buildQuery.and(NdViewSpecification.searchStore(
                        dungSearchParam.store().trim()));
            }
            if (stringIsValid(dungSearchParam.username())) {
                buildQuery.and(NdViewSpecification.searchParam(
                        dungSearchParam.userStatus()));
            }
        }
        return ndRepo.findAllNdView(pageable, buildQuery);
    }

    private boolean stringIsValid(String string) {
        return string != null && !string.isBlank() && !string.isEmpty();
    }
}
