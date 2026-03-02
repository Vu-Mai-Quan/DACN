package com.example.dacn.config;

import com.example.dacn.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class FilterChainConfig {

    private final JwtService jwtService;

    @Bean
//    @Order(1)
    SecurityFilterChain filterChainSpam(HttpSecurity http) throws Exception {
        http
                .addFilterAt(new SpamFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .csrf(CsrfConfigurer::disable).cors(CorsConfigurer::disable)
                .authorizeHttpRequests(rq -> 
                        rq.anyRequest().permitAll());
        return http.build();
    }

    @Order(2)
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.securityMatcher(rq->{
//            Optional.ofNullable(rq.getCookies("")).map(item-> item.).orElse(false);
        return true;
        })
                .addFilterBefore(new JwtFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .csrf(CsrfConfigurer::disable).cors(CorsConfigurer::disable)
                .authorizeHttpRequests(rq -> {
                    rq.anyRequest().authenticated();
                });
        return httpSecurity.build();
    }
}
