package com.example.dacn.config;

import com.example.dacn.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class FilterChainConfig {

    private final JwtService jwtService;

    @Bean
    @Order(1)
    SecurityFilterChain filterChainSpam(HttpSecurity http) throws Exception {
        http.securityMatcher("/auth/login")
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
        httpSecurity.securityMatcher("/*/jwt/*")
                .addFilterAt(new JwtFilter(authenticationManager),
                        UsernamePasswordAuthenticationFilter.class)
                .csrf(CsrfConfigurer::disable).cors(CorsConfigurer::disable)
                .authorizeHttpRequests(rq -> {
                    rq.anyRequest().authenticated();
                }).sessionManagement(sess -> {
            sess.sessionCreationPolicy(STATELESS);

        });
        return httpSecurity.build();
    }
}
