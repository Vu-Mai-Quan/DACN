package com.example.dacn.config;

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
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class FilterChainConfig {
	@Bean
	@Order(1)
	SecurityFilterChain filterChainSpam(HttpSecurity http,
			OncePerRequestFilter spamFilter) throws Exception {
		http.securityMatcher("/api/v1/auth/login", "/api/v1/auth/refresh-token")
				.addFilterBefore(spamFilter, UsernamePasswordAuthenticationFilter.class)
				.csrf(CsrfConfigurer::disable).cors(CorsConfigurer::disable)
				.authorizeHttpRequests(rq -> rq.anyRequest().permitAll());
		return http.build();
	}

	@Order(2)
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity,
			OncePerRequestFilter jwtFilter) throws Exception {
		httpSecurity.securityMatcher("/api/v1/jwt/*")
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.csrf(CsrfConfigurer::disable).cors(CorsConfigurer::disable)
				.authorizeHttpRequests(rq -> {
					rq.anyRequest().authenticated();
				});
		return httpSecurity.build();
	}
}
