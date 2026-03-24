/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.config;

import com.example.dacn.config.BearerAuthenticationToken.BearerPrincipal;
import com.example.dacn.db1.model.ChucVu;
import com.example.dacn.service.JwtService;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.dacn.service.JwtService.TypeToken.ACCESS;

/**
 *
 * @author ADMIN
 */
public final class BearerAuthenticationProvider implements
        AuthenticationProvider {
    
    private final JwtService<?> jwtService;
    
    public BearerAuthenticationProvider(final JwtService<?> jwtService) {
        Assert.notNull(jwtService, "jwtService không được phép null");
        this.jwtService = jwtService;
    }
    
    @Override
    public Authentication authenticate(Authentication authentication) throws JwtException {
        Assert.isInstanceOf(BearerAuthentication.class, authentication,
                "This provider not suppots");
        Assert.notNull(authentication, "authentication is not null");
        BearerAuthentication bearerAuthentication = (BearerAuthentication) authentication;
        var passerToken = jwtService.paseJwt(
                bearerAuthentication.getPrincipal().toString());
        var isAccess = passerToken.getHeader().get(Header.TYPE).toString().equals(
                ACCESS.name());
        Assert.isTrue(isAccess, "Token phải là access");
        var readProps = passerToken.getBody();

        Optional<Object> rolesIsExist = Optional.ofNullable(readProps.get(
                "roles"));

        List<?> roles = (List<?>) rolesIsExist.orElseGet(List::of);

        var setAuthorities = roles.stream().map(
                String.class::cast).map(ChucVu.RoleName::castStringToRole).collect(
                Collectors.toSet());

        BearerPrincipal bearerPrincipal = new BearerPrincipal() {
        };
        bearerPrincipal.getTokenProperties().putAll(readProps);

        AbstractBearerToken bearerTokenSuccess = new BearerAuthenticationToken(
                authentication.toString(), UUID.fromString(
                readProps.getSubject()), bearerPrincipal, setAuthorities);
        if (bearerTokenSuccess.getDetails() == null) {
            bearerAuthentication.setDetails(
                    bearerAuthentication.getDetails());
        }
        return bearerTokenSuccess;
        
    }
    
    @Override
    public boolean supports(
            Class<?> authentication) {
        return BearerAuthentication.class.isAssignableFrom(authentication);
    }
    
}
