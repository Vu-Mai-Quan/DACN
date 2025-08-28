/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.config;

import com.example.dacn.basetemplate.ErrorResponse;
import com.example.dacn.db1.repositories.TaiKhoanRepo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * @author ADMIN
 */
@Configuration
@AllArgsConstructor
public class UserDetailConfig {

    private final TaiKhoanRepo taiKhoanRepo;

    @Bean
    protected PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        return (username) -> taiKhoanRepo.timTaiKhoanTheoEmail(username).orElseThrow(() -> new UsernameNotFoundException("Thông tin tài khoản hoặc mật khẩu không chính xác"));
    }

    //

    @Bean
    protected AuthenticationProvider authenticationUserDetailsService() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(encoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }

    @Bean
    protected AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected AuthenticationFailureHandler authenticationFailureHandler() {
        return (rq, rp, exception) -> {
            rp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            rp.setContentType("application/json");
            rp.setHeader("Access-Control-Allow-Origin", "*");
            var errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED)
                    .url(rq.getRequestURI())
                    .message("Người dùng chưa được xác thực")
                    .data(null)
                    .build();
            rp.getWriter().write(errorResponse.toString());
        };
    }

}
