package com.example.sequrity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;


@PropertySource("classpath:application.properties")
@EnableAutoConfiguration
@Configuration
@ComponentScan
@EnableJdbcHttpSession
public class Seq {

    public static void main(String[] args) {
        SpringApplication.run(Seq.class, args);
    }


}

