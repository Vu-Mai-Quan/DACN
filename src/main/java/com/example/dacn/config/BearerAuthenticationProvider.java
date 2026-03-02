/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.config;

import com.example.dacn.service.JwtService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

/**
 *
 * @author ADMIN
 */
public final class BearerAuthenticationProvider implements
        AuthenticationProvider {

    private final JwtService jwtService;

    public BearerAuthenticationProvider(final JwtService jwtService) {
        Assert.notNull(jwtService, "jwtService không được phép null");
        this.jwtService = jwtService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(BearerAuthentication.class, authentication,
                "This provider not suppots");
        BearerAuthentication bearerAuthentication = BearerAuthentication.class.cast(
                authentication);
        
        
        return bearerAuthentication;
    }

    @Override
    public boolean supports(
            Class<?> authentication) {
        return BearerAuthentication.class.isAssignableFrom(authentication);
    }

}
