/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.config;

import com.example.dacn.db1.repositories.TaiKhoanRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

//    @Bean
//    protected UserDetailsService userDetailService(TaiKhoanRepo khoanRepo) {
//        return (username) -> khoanRepo.timTaiKhoanTheoEmail(username).get();
//    }
//
//    @Bean
//    protected AuthenticationProvider authenticationProvider(PasswordEncoder encoder, UserDetailsService detailsService) {
//        DaoAuthenticationProvider dap = new DaoAuthenticationProvider(encoder);
//        dap.setUserDetailsService(detailsService);
//        dap.setPasswordEncoder(encoder);
//        return dap;
//    }
//
//
    @Bean
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration ac) throws Exception {
        return ac.getAuthenticationManager();
    }
}
