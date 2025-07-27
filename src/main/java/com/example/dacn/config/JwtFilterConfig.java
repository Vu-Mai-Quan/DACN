/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.config;

import com.example.dacn.basetemplate.ErrorResponse;
import com.example.dacn.service.IJwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author ADMIN
 * Lớp được tạo ra để kiểm tra token có hợp lệ để đi tiếp không 
 */
@Configuration
@RequiredArgsConstructor
public class JwtFilterConfig extends OncePerRequestFilter{

    private final IJwtService IJwtService;
    private final ObjectMapper mapper;
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return super.shouldNotFilter(request); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(!header.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        StringBuffer buffer = new StringBuffer(header.substring(7));

      
        
    }
    
    
    private void sendErrorResponse(HttpServletRequest rq, HttpServletResponse response, String message) throws IOException{
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, rq.getRequestURI(), message, new Date(System.currentTimeMillis()));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
    
    
    
    
}
