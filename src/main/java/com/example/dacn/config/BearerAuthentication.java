/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.config;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.util.Assert;

/**
 *
 * @author ADMIN
 */
public final class BearerAuthentication extends AbstractAuthenticationToken {


    private final Object token;

    public BearerAuthentication(Object token) {
        super(null);
        this.token = token;

        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return token; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object getPrincipal() {
        return token; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        Assert.isTrue(authenticated, "no trust authenticated from children");
        super.setAuthenticated(false); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
