package com.example.dacn.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dacn.service.NguoiDungService;
import com.example.dacn.service.NguoiDungService.LoginDto;
import java.time.Duration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

@RestController
@RequestMapping("/auth/")

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    NguoiDungService dungService;
    long timeAgeAccess, timeAgeRefresh;

    public AuthController(NguoiDungService dungService,
            @Value("${init-data.token-access.expire-date}") int timeAgeAccess,
            @Value("${init-data.token-refresh.expire-date}") int timeAgeRefresh) {
        this.dungService = dungService;
        this.timeAgeAccess = timeAgeAccess;
        this.timeAgeRefresh = timeAgeRefresh;
    }

    @GetMapping("login")
    public ResponseEntity<Object> login(@RequestBody LoginDto param) {
        var resLogin = dungService.login(param);
        ResponseCookie accessToken = ResponseCookie.from("accessToken",
                resLogin.accessToken())
                .sameSite("lax")
                .httpOnly(true)
                .path("/")
                .secure(true)
                .maxAge(Duration.ofMinutes(this.timeAgeAccess))
                .build(), refreshToken = ResponseCookie.from("refreshToken",
                        resLogin.refreshToken())
                        .sameSite("lax")
                        .httpOnly(true)
                        .path("/")
                        .secure(true)
                        .maxAge(Duration.ofMinutes(this.timeAgeRefresh))
                        .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessToken.toString(),
                        refreshToken.toString()).body(
                resLogin.nguoiDung());
    }

}
