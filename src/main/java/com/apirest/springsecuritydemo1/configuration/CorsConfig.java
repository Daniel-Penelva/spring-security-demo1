package com.apirest.springsecuritydemo1.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/usuario/**")
                        .allowedOrigins("*") // Permit all origins
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow common HTTP methods
                        .allowedHeaders("*"); // Permit all headers
            }
        };
    }
}