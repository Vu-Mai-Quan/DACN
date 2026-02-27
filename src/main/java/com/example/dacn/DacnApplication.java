package com.example.dacn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class DacnApplication {
    
    public static void main(String[] args) {
        System.out.println(System.getenv("PORT"));
        SpringApplication.run(DacnApplication.class, args);
    }
    
}
