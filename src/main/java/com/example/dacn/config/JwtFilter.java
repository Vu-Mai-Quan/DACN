package com.example.dacn.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URI;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final AuthenticationManager authedManager;

    JwtFilter(AuthenticationManager authenticationManager) {
        this.authedManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException , JwtException{
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
        } catch (AuthenticationException | JwtException e) {
            ProblemDetail problemDetail = ProblemDetail.forStatus(401);
            problemDetail.setTitle("401: Unauthorized");
            problemDetail.setDetail("User not authorized");
            problemDetail.setInstance(URI.create(request.getRequestURI()));
            switch (e) {
                case InvalidKeyException ignored -> problemDetail.setDetail("Khóa token bị lỗi");
                case ExpiredJwtException ignored -> problemDetail.setDetail("Token đã hết hạn xử dụng");
                case SignatureException ignored -> problemDetail.setDetail("Khóa bảo mật không hợp lệ");
                default -> problemDetail.setDetail(e.getMessage());
            }
            request.setAttribute("problemDetail", problemDetail);
            filterChain.doFilter(request, response);
        }

    }


}
