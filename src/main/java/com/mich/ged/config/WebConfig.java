package com.mich.ged.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Applique la règle sur toutes les routes (/api/...)
                        .allowedOrigins("http://localhost:3000", "http://localhost:3001", "http://localhost:3333", "http://ged.mink67.com") // URL de votre Next.js
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .allowedHeaders("*")
                        .exposedHeaders("Authorization", "Content-Disposition")
                        .allowCredentials(true) // Nécessaire si vous envoyez des cookies ou des headers d'authentification
                        .maxAge(3600); // Durée de mise en cache du Preflight (en secondes)
            }
        };
    }
}