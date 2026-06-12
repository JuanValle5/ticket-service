package com.vivaeventos.boletas_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class BoletasServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoletasServiceApplication.class, args);
        //amoo wtiq aoke udmt
    }

}
