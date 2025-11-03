package com.example.dacn;

import com.example.dacn.mapper.MapperClass;
import com.example.dacn.model.Source;
import org.springframework.beans.factory.annotation.Autowired;
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
        System.out.println(System.getenv("TEST"));
        var app = SpringApplication.run(DacnApplication.class, args);
        Source source = new Source(12, "Vũ Mai","Quân");
        System.out.println(app.getBean(MapperClass.class).toDestination(source));
    }

}
