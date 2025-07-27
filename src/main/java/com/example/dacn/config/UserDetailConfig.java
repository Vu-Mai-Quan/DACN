/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.config;

import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 *
 * @author ADMIN
 */
@Configuration
public class UserDetailConfig {

    @Bean
    protected PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Bean
    protected UserDetailsService userDetailService() {
        User u = new User("ADMIN", encoder().encode("1234"), Set.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_CUSTOMER"), new SimpleGrantedAuthority("ROLE_MANAGER")));
        return new InMemoryUserDetailsManager(u);
    }

    @Bean
    protected AuthenticationProvider authenticationProvider(PasswordEncoder encoder) {
        DaoAuthenticationProvider dap = new DaoAuthenticationProvider(encoder);
        dap.setUserDetailsService(userDetailService());
        return dap;
    }

    ;
    
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration ac) throws Exception {
        return ac.getAuthenticationManager();
    }
}
