/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.service.imlp;

import com.example.dacn.basetemplate.dto.request.LoginDto;
import com.example.dacn.basetemplate.dto.response.LoginResponse;
import com.example.dacn.basetemplate.dto.response.TokenAndExpriredView;
import com.example.dacn.db1.model.RefreshToken;
import com.example.dacn.db1.model.TaiKhoan;
import com.example.dacn.db1.repositories.RefreshTokenRepo;
import com.example.dacn.db1.repositories.TaiKhoanRepo;
import com.example.dacn.service.IAuthService;
import com.example.dacn.service.IJwtService;
import jakarta.persistence.EntityNotFoundException;
import java.sql.Date;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ADMIN
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthServiceImlp implements IAuthService {

    TaiKhoanRepo taikhoanRepo;
    IJwtService jwtSevice;
    PasswordEncoder encode;
    RefreshTokenRepo rfTkRp;

    @Override
    public LoginResponse login(LoginDto loginDto) {
        var tk = taikhoanRepo.timTaiKhoanTheoEmail(loginDto.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Thông tin tài khoản hoặc mật khẩu không chính xác"));
        if (!encode.matches(loginDto.getPassword(), tk.getPassword())) {
            throw new EntityNotFoundException("Thông tin tài khoản hoặc mật khẩu không chính xác");
        }
        Optional<TokenAndExpriredView> rfRp = rfTkRp.getNewToken(tk.getId());
        String rf = rfRp.isPresent() && new Date(rfRp.get().getExprired()).after(new Date(System.currentTimeMillis())) ? rfRp.get().getToken() : insertRefreshToken(jwtSevice.createRefreshToken(tk), tk);
        String token = jwtSevice.createToken(tk);
        return LoginResponse.builder()
                .token(token)
                .refreshToken(rf)
                .tenTaiKhoan(tk.getUsername())
                .build();
    }

    @Transactional(transactionManager = "db2TransactionManager")
    private String insertRefreshToken(String token, TaiKhoan idTaiKhoan) {
        rfTkRp.save(new RefreshToken(idTaiKhoan, jwtSevice.exprired(token).getTime(), token));
        return token;
    }

}
