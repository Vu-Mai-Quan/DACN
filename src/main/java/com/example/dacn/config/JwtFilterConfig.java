/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.config;

import com.example.dacn.basetemplate.ErrorResponse;
import com.example.dacn.service.IJwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.Set;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.manager.Constants;
import org.apache.tomcat.util.buf.CharsetUtil;
import org.modelmapper.internal.Pair;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author ADMIN Lớp được tạo ra để kiểm tra token có hợp lệ để đi tiếp không
 */
@Configuration
@RequiredArgsConstructor
public class JwtFilterConfig extends OncePerRequestFilter {

    private final IJwtService iJwtService;
    private final ObjectMapper mapper;

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        Pair<String, Set<String>> pair = Pair.of("*/client/*", Set.of("GET", "POST", "PUT", "DELETE"));
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        String servletPath = request.getRequestURL().toString();
        String method = request.getMethod();
        return antPathMatcher.match(pair.getLeft(), servletPath)
                && pair.getRight().stream().anyMatch(m -> m.equalsIgnoreCase(method));
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            try {
                String token = header.substring(7);
                if (iJwtService.isRefreshToken(token)) {
                    sendErrorResponse(request, response, "Token không hợp lệ");
                    return;
                }
                UserDetails userDetails = iJwtService.kiemTraTaiKhoanTrongToken(token);
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                sendErrorResponse(request, response, e.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response);

    }

    private void sendErrorResponse(HttpServletRequest rq, HttpServletResponse response, String message) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, rq.getRequestURI(), message, new Date(System.currentTimeMillis()));
        response.setStatus(HttpStatus.OK.value());
        response.setCharacterEncoding(Constants.CHARSET);
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }

}
