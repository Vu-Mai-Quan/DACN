/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

/**
 * @author ADMIN
 */
@Configuration
@Profile({"dev","test"})
public class OpenApiConfig {

    @Bean
    protected GroupedOpenApi groupedOpenApi(@Value("${openapi.service.api-docs}") String apiDoc) {
        return GroupedOpenApi.builder()
                .group(apiDoc)
                .packagesToScan("com.example.dacn.controller")
                .build();
    }

    @Bean
    protected OpenAPI openAPI(@Value("${openapi.service.version}") String version, @Value("${openapi.service.url}") String url, @Value("${openapi.service.title}") String title) {
        final String securitySchema = "bearerAuth";
        return new OpenAPI().servers(List.of(new Server().description(title).url(url)))
                .components(new Components().
                        addSecuritySchemes(securitySchema, new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer").bearerFormat("JWT")))
                .security(List.of(new SecurityRequirement().addList(securitySchema))).info(new Info()
                        .description("Tài liệu cho dự án cá nhân")
                        .license(new License().name("Apache 2.0").url("https://springdoc.org"))
                        .version(version));
    }
}
