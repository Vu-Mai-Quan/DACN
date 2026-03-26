package com.example.dacn.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;


public interface JwtService<T> {

    String createJwt(T jwt);

    Map<String, ?> getTokenProperties(String token);

    Jws<Claims> paseJwt(String jwt) throws JwtException;

    enum TypeToken {
        REFRESH, ACCESS;
    }

    Header<?> getHeader(String jwt);


    record  ParamJwt<T extends UserDetails>(T param, TypeToken typeToken) {


    }



}
