package com.app.cultural_center_management;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import javax.crypto.SecretKey;


@SpringBootApplication
public class CulturalCenterManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(CulturalCenterManagementApplication.class, args);
    }

    @Bean
    public SecretKey secretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

}
