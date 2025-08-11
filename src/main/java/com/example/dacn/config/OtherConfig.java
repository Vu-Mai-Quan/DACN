/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author ADMIN
 */
@Configuration
public class OtherConfig {

    @Bean
    protected ModelMapper modelMapper() {
        return new ModelMapper();
    }
    
    @Bean
    protected ObjectMapper objectMapper() {
        var o = new ObjectMapper();
        //đăng kí để có thể tuần tự hóa LocalDateTime
        o.registerModule(new JavaTimeModule());
        o.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return o;
    }

}

