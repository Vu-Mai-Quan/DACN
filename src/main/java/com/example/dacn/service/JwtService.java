package com.example.dacn.service;

import com.example.dacn.db1.model.viewmodel.NguoiDungView;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;

public interface JwtService<T> {

    String createJwt(T jwt);

    Map<String, ?> getTokenProperties(String token);

    public static enum TypeToken {
        REFRESH, ACCESS;
    }

    public static record ParamJwt (NguoiDungView nguoiDungView, TypeToken typeToken){

        
    }
}
