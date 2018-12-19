package com.example.sequrity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@PropertySource("classpath:application.properties")
@SpringBootApplication
public class Seq {

    public static void main(String[] args) {
        SpringApplication.run(Seq.class, args);
    }

}

