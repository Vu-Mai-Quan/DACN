package com.example.dacn.controller;

import com.example.dacn.service.NguoiDungService;
import com.example.dacn.service.NguoiDungService.LoginDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

import static com.example.dacn.service.JwtService.TypeToken.REFRESH;

@RestController
@RequestMapping("/auth/")

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthController {

    NguoiDungService dungService;
    @Value("${init-data.token-refresh.expire-date}")
    long timeAgeRefresh;

    @GetMapping("public/login")
    @Operation(summary = "Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDto param) {
        var resLogin = dungService.login(param);
        ResponseCookie refreshToken = ResponseCookie.from(
                        REFRESH.name(),
                        resLogin.refreshToken())
                .sameSite("lax")
                .httpOnly(true)
                .path("/")
                .secure(true)
                .maxAge(Duration.ofMinutes(this.timeAgeRefresh))
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,
                        refreshToken.toString()).body(
                        "{\"tokenAccess\":\"%s\"}".formatted(resLogin.accessToken()));
    }

}
