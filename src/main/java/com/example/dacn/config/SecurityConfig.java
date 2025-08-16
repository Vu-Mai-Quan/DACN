/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.config;

import com.example.dacn.basetemplate.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

/**
 * @author ADMIN
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final ObjectMapper objectMapper;
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity, JwtFilterConfig config) throws Exception {

        return httpSecurity
                .addFilterBefore(config, UsernamePasswordAuthenticationFilter.class)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(rq -> rq
                        //                            .requestMatchers(HttpMethod.GET, "api/v1/user/*").hasAuthority("ADMIN")
                        .requestMatchers("/admin/*").hasRole("ADMIN").anyRequest().permitAll())
                .cors(AbstractHttpConfigurer::disable)
                .exceptionHandling(t->{
                    t.authenticationEntryPoint((rq, rp, au)->{
                        sendErrorResponse(rq,rp,au,"Đường dẫn cần xác thực người dùng",HttpStatus.FORBIDDEN);
                    });
                    t.accessDeniedHandler((rq, rp, au)->{
                        sendErrorResponse(rq,rp,au,"Không có quyền truy cập",HttpStatus.UNAUTHORIZED);
                    });
                })
                .build();
    }

    private <T extends Exception> void sendErrorResponse(HttpServletRequest rq, HttpServletResponse rp, T authenticationException, String message, HttpStatus status) throws IOException {
        rp.setStatus(status.value());
        rp.setContentType("application/json");
        rp.setCharacterEncoding("UTF-8");
        rp.setHeader("Access-Control-Allow-Origin", "*");
        var errorResponse = ErrorResponse.builder()
                .status(status)
                .url(rq.getRequestURI())
                .message(message)
                .data(authenticationException.getMessage())
                .build();
        rp.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

}
