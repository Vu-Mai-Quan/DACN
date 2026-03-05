/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.config;

import com.example.dacn.db1.model.ChucVu;
import com.example.dacn.service.JwtService;
import io.jsonwebtoken.JwtException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

import com.example.dacn.config.BearerAuthenticationToken.BearerPrincical;
import static com.example.dacn.service.JwtService.TypeToken.ACCESS;
import io.jsonwebtoken.Header;
import java.util.List;

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
    @SuppressWarnings("unchecked")
    public Authentication authenticate(Authentication authentication)  {
        Assert.isInstanceOf(BearerAuthentication.class, authentication,
                "This provider not suppots");
        Assert.notNull(authentication, "authentication is not null");
        BearerAuthentication bearerAuthentication = BearerAuthentication.class.cast(
                authentication);
        var paserToken = jwtService.paseJwt(
                bearerAuthentication.getPrincipal().toString());
        var isAccess = paserToken.getHeader().get(Header.TYPE).toString().equals(
                ACCESS.name());
        Assert.isTrue(isAccess, "Token phải là access");
        try {
            var readProps = paserToken.getBody();
            
            Optional<Object> rolesIsExist = Optional.ofNullable(readProps.get("roles"));
            
            List<?> roles = (List<?>) rolesIsExist.orElseGet(() -> List.of());
            
            var setAuthorites = roles.stream().filter(String.class::isInstance).map(
                    String.class::cast).map(ChucVu.RoleName::castStringToRole).collect(
                    Collectors.toSet());
            
            BearerPrincical bearerPrincical = new BearerPrincical() {};
            bearerPrincical.getTokenProperties().putAll(readProps);
            
            AbstractBearerToken bearerTokenSucces = new BearerAuthenticationToken(
                    authentication.toString(), bearerPrincical, setAuthorites);
            if (bearerTokenSucces.getDetails() == null) {
                bearerAuthentication.setDetails(
                        bearerAuthentication.getDetails());
            }
            return bearerTokenSucces;
        } catch (JwtException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
        
    }
    
    @Override
    public boolean supports(
            Class<?> authentication) {
        return BearerAuthentication.class.isAssignableFrom(authentication);
    }
    
}
