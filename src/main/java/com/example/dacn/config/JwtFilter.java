package com.example.dacn.config;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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

    private final AuthenticationManager authedManager;

    JwtFilter(AuthenticationManager authenticationManager) {
        this.authedManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        boolean isTokenExist = request.getHeader("Authorization") == null || request.getHeader(
                "Authorization").isEmpty() || request.getHeader(
                "Authorization").isBlank();
        String token = isTokenExist
                ? null : request.getHeader("Authorization").substring(7);

        try {
            if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var authentication = new BearerAuthentication(token);
                authentication.setDetails(new WebAuthenticationDetails(request));
                AbstractBearerToken bearerAuthed = (AbstractBearerToken) authedManager.authenticate(authentication);
                SecurityContextHolder.getContext().setAuthentication(bearerAuthed);
            }
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            throw new AuthenticationServiceException("token không hợp lệ", e);
        }
    }


}
