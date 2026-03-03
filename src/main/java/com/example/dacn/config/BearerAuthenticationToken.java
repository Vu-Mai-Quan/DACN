/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.config;

import static com.example.dacn.service.JwtService.TypeToken.ACCESS;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

/**
 *
 * @author ADMIN
 */
public class BearerAuthenticationToken extends AbstractBearerToken {

    private final Map<String, ?> tokenProperties;

    BearerAuthenticationToken(JwtAuthentication authentication,
            BearerPrincical bearerPrincical, Collection<? extends GrantedAuthority> authorities) {
        super(authentication.token(), null, bearerPrincical,
                authorities);
        Assert.isTrue(authentication.typeToken() == ACCESS,
                "credentials must be a ACCESS token");
        this.tokenProperties = Collections.unmodifiableMap(
                bearerPrincical.getTokenProperties());
    }

    @Override
    public Map<String, ?> getTokenProperties() {
        return tokenProperties; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @RequiredArgsConstructor
    @Getter
    public static abstract class BearerPrincical implements Principal {

        private final Map<String, Object> tokenProperties = new HashMap<>();

        @SuppressWarnings("unchecked")
        public <A> A getAttribute(String name) {
            return (A) this.tokenProperties.get(name);
        }

    
    }

    @RequiredArgsConstructor
    public static class BearerPrincicalImlp extends BearerPrincical {

        private final String username;

//        @Override
//        @SuppressWarnings("unchecked")
//        public Collection<? extends GrantedAuthority> getAuthoritys() {
//            Object roles = getAttribute("roles");
//
//            if (!(roles instanceof Collection)) {
//                return Collections.emptySet();
//            }
//            for (var item : (Collection<?>) roles) {
//                if (!(item instanceof GrantedAuthority)) {
//                    return Collections.emptySet();
//                }
//            }
//            return (Collection<? extends GrantedAuthority>) roles;
//        }

        @Override
        public String getName() {

            return Objects.requireNonNullElse(
                    username.isBlank() ? "anonymous" : username,
                    "anonymous");
        }
    }
}
