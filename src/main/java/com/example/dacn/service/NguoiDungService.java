package com.example.dacn.service;

import com.example.dacn.db1.model.viewmodel.NguoiDungView;
import com.example.dacn.template.dto.NguoiDungDto;
import com.example.dacn.template.enumModel.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;
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

    record LoginDto(
            @Schema(description = "Tên đăng nhập", example = "admin") @NotBlank(message = "Username không được để " +
                    "trống") @Email(message = "Username không hợp lệ") String username,
            @Schema(description = "Mật khẩu", example = "123456") @Length(min = 6, max = 20, message = "Password " +
                    "không được để trống") String password) {
    }

    @Builder
    record LoginResponse(String refreshToken, String accessToken) {

    }
}
