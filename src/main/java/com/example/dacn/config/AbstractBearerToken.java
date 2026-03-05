/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.config;

import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

/**
 *
 * @author ADMIN
 */
public abstract class AbstractBearerToken extends AbstractAuthenticationToken {

   
    
    private final Object credentials;
    private Object principal;
    @Getter
    private final String token;

    protected AbstractBearerToken(String token, Object credentials,
            Object principal,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        Assert.notNull(principal, "principal not null");
        Assert.hasText(token, "Token not emty");
        this.credentials = credentials;
        this.principal = principal;
        this.token = token;
        super.setAuthenticated(true);
    }

    protected AbstractBearerToken(String token,
            Collection<? extends GrantedAuthority> authorities) {
        this(token, token, token, authorities);
        super.setAuthenticated(true);
    }
    @Override
    public void setAuthenticated(boolean authenticated) {
        Assert.isTrue(authenticated, "no trust authenticated from children");
        super.setAuthenticated(false); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    @Override
    public Object getCredentials() {
        return credentials;// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object getPrincipal() {
        return principal; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public abstract Map<String, ?> getTokenProperties();

}
