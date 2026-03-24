package com.example.dacn.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
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
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class FilterChainConfig {


    private final String[] path;
    private final ObjectMapper objectMapper;

    public FilterChainConfig(Environment environment, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.path = environment.matchesProfiles("dev") ? new String[]{
                "/swagger-ui/**", "/swagger-ui.html", "/swagger-resources/**", "/v3/api-docs", "/v3/api-docs/*"
        } : new String[]{};
    }

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
                            .requestMatchers(path).permitAll()
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
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        var map = Map.of("message", "Access Denied", "errorMessage:", e.getMessage());
        httpServletResponse.getWriter()
                .write(map.toString())
        ;

    }

    private void authenticationEntryPoint(@NonNull HttpServletRequest httpServletRequest,
                                          @NonNull HttpServletResponse httpServletResponse,
                                          AuthenticationException e) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType("application/json");
        var pro = httpServletRequest.getAttribute("problemDetail");
        httpServletResponse.setCharacterEncoding("UTF-8");
        if (pro != null) {
            httpServletResponse.getWriter().write(this.objectMapper.writeValueAsString(pro));
        } else {
            Map<String, Object> map = Map.of("message", "User not authorized", "errorMessage:", e.getMessage());
            httpServletResponse.getWriter()
                    .write(map.toString());
        }

    }


    ;
}
