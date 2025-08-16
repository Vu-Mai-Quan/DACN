/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.config;

import com.example.dacn.db1.repositories.TaiKhoanRepo;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author ADMIN
 */
@Configuration
@AllArgsConstructor
public class UserDetailConfig {

    private final TaiKhoanRepo taiKhoanRepo;

    @Bean
    protected PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(4);
    }

//    @Bean
//    protected UserDetailsService userDetailsService() {
//        return (username) -> taiKhoanRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
//    }
//
//    @Bean
//    protected AuthenticationManager authenticationManager() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(encoder());
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
//        return new ProviderManager(daoAuthenticationProvider);
//    }

//    @Bean
//    protected AuthenticationFailureHandler authenticationFailureHandler() {
//        return (rq, rp, exception) -> {
//            rp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            rp.setContentType("application/json");
//            rp.setHeader("Access-Control-Allow-Origin", "*");
//            var errorResponse = ErrorResponse.builder()
//                    .status(HttpStatus.UNAUTHORIZED)
//                    .url(rq.getRequestURI())
//                    .message("Người dùng chưa được xác thực")
//                    .data(null)
//                    .build();
//            rp.getWriter().write(errorResponse.toString());
//        };
//    }

}
