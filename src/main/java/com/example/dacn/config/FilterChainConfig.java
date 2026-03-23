package com.example.dacn.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class FilterChainConfig {


    @Bean
    @Order(1)
    SecurityFilterChain filterChainSpam(HttpSecurity http) throws Exception {
        http.securityMatcher("/*/public/*")
                .addFilterAt(new SpamFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .csrf(CsrfConfigurer::disable).cors(CorsConfigurer::disable)
                .authorizeHttpRequests(rq
                        -> rq.anyRequest().permitAll());
        return http.build();
    }

    @Order(2)
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity,
                                    AuthenticationManager authenticationManager) throws Exception {
        httpSecurity
                .addFilterAt(new JwtFilter(authenticationManager),
                        UsernamePasswordAuthenticationFilter.class)
                .csrf(CsrfConfigurer::disable).cors(CorsConfigurer::disable)
                .authorizeHttpRequests(rq -> {
                    rq.requestMatchers("/store/*")
                            .hasAnyAuthority("SYSTEM_ADMIN")
                            .requestMatchers("/product/*").hasAnyAuthority(
                                    "SYSTEM_ADMIN", "STORE_OWNER", "STAFF")
                            .anyRequest().authenticated();
                })
                .sessionManagement(sessions ->
                        sessions.sessionCreationPolicy(STATELESS)).exceptionHandling(ex -> {
                    ex.authenticationEntryPoint(this::authenticationEntryPoint);
                    ex.accessDeniedHandler(this::accessDeniedHandle);
                });
        return httpSecurity.build();
    }

    private void accessDeniedHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    AccessDeniedException e) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter()
                .write("{\"message\": \"Access Denied\", \"errorMessage:\": %s}".formatted(e.getMessage()))
        ;

    }

    private void authenticationEntryPoint(HttpServletRequest httpServletRequest,
                                          HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter()
                .write("{\"message\": \"User not authorized\", \"errorMessage:\": %s}".formatted(e.getMessage()))
        ;
    }


    ;
}
