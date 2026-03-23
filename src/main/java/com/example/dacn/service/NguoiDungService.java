package com.example.dacn.service;

import com.example.dacn.db1.model.viewmodel.NguoiDungView;
import com.example.dacn.template.dto.NguoiDungDto;
import com.example.dacn.template.enumModel.UserStatus;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NguoiDungService {

    boolean createNguoiDung(NguoiDungDto dung);

    LoginResponse login(LoginDto login);

    @Builder
    record NguoiDungViewParamSearch(String username,
            String store, UserStatus userStatus) {

    }

    Page<NguoiDungView> readAllNd(Pageable pageable,
            NguoiDungViewParamSearch dungViewParamSearch);

    record LoginDto(String username, String password) {

    }

    @Builder
    record LoginResponse(String refreshToken, String accessToken) {

    }
}
