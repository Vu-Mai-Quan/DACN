package com.example.dacn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DacnApplication {

	public static void main(String[] args) {
        System.out.println(args[0]);
        System.out.println(System.getProperty("spring.profiles.active"));
        System.out.println(System.getenv("NAME"));
		SpringApplication.run(DacnApplication.class, args);
	}

}
