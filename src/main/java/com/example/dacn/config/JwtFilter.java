package com.example.dacn.config;

import static com.example.dacn.service.JwtService.TypeToken.ACCESS;
import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenManager;

    JwtFilter(AuthenticationManager authenticationManager) {
        this.authenManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        Cookie[] cookies = request.getCookies();
        String token = null;
        for (var item : cookies) {
            if (item.getName().equals(ACCESS.name()) && item.getValue() != null && !item.getValue().isBlank()) {
                token = item.getValue();
                break;
            }
        }
        try {
            if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var authentication = new BearerAuthentication(token);
                authentication.setDetails(new WebAuthenticationDetails(request));
                AbstractBearerToken bearerAuthen = (AbstractBearerToken) authenManager.authenticate(
                        authentication);
                SecurityContextHolder.getContext().setAuthentication(bearerAuthen);
               
            }
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            throw new AuthenticationServiceException("token không hợp lệ", e);
        }
    }

}
