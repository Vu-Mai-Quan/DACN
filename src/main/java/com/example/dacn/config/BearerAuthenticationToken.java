/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.*;

/**
 *
 * @author ADMIN
 */
public final class BearerAuthenticationToken extends AbstractBearerToken {

    private final Map<String, ?> tokenProperties;

    BearerAuthenticationToken(String authentication,UUID id,
            BearerPrincical bearerPrincical, Collection<? extends GrantedAuthority> authorities) {
        super(authentication, id, bearerPrincical,
                authorities);
     
        this.tokenProperties = Collections.unmodifiableMap(
                bearerPrincical.getTokenProperties());
    }

    @Override
    public Map<String, ?> getTokenProperties() {
        return tokenProperties; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getName() {
        Object username = this.tokenProperties.get("username");

        if (username instanceof String s) {
            return s;
        }

        return "anonymous";
    }

    @Override
    public boolean implies(Subject sbjct) {
        return super.implies(sbjct); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    

    @RequiredArgsConstructor
    @Getter
    public static abstract class BearerPrincical  {

        private final Map<String, Object> tokenProperties = new HashMap<>();

        @SuppressWarnings("unchecked")
        public <A> A getAttribute(String name) {
            return (A) this.tokenProperties.get(name);
        }

    
    }

    @RequiredArgsConstructor
    public static class BearerPrincicalImlp{

     

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

      
    }
}
