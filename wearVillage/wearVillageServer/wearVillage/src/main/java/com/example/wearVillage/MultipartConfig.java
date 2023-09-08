package com.example.wearVillage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@Configuration
public class MultipartConfig {
    @Bean
    public StandardServletMultipartResolver resolver() {
        return new StandardServletMultipartResolver();
    }
}