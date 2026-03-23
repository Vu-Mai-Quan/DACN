package com.example.dacn.service;

import com.example.dacn.db1.model.viewmodel.NguoiDungView;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;

import java.util.Map;


public interface JwtService<T> {

    String createJwt(T jwt);

    Map<String, ?> getTokenProperties(String token);

    Jws<Claims> paseJwt(String jwt);

    enum TypeToken {
        REFRESH, ACCESS;
    }

    Header<?> getHeader(String jwt);


    record ParamJwt(NguoiDungView nguoiDungView, TypeToken typeToken) {


    }
}
