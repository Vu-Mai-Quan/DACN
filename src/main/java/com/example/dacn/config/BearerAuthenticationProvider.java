/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.config;

import com.example.dacn.db1.model.ChucVu;
import com.example.dacn.service.JwtService;
import io.jsonwebtoken.JwtException;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
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

        try {
            var readProps = jwtService.getTokenProperties(
                    (String) bearerAuthentication.getPrincipal());
            var rolesIsExist = Optional.of(readProps.get("roles"));
            Set<?> roles = (Set<?>) rolesIsExist.orElseGet(() -> Set.of());
            var setAuthorites = roles.stream().filter(String.class::isInstance).map(
                    String.class::cast).map(ChucVu.RoleName::castStringToRole).collect(
                    Collectors.toSet());
//            AbstractBearerToken bearerTokenSucces =  new  BearerAuthenticationToken(authentication, bearerPrincical, setAuthorites);
        } catch (JwtException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }

        return bearerAuthentication;
    }

    @Override
    public boolean supports(
            Class<?> authentication) {
        return BearerAuthentication.class.isAssignableFrom(authentication);
    }

}
