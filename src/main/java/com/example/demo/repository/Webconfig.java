package com.example.demo.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Webconfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Erlaube CORS-Anfragen von localhost:5173 (Frontend)
        registry.addMapping("/api/**")  // Hier das Backend-Pfad-Muster, das du verwendest
                //.allowedOrigins("*") // Frontend-Origin
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Die Methoden, die du zulässt
                .allowedHeaders("*") // Alle Header zulassen
                .allowCredentials(true); // Optionale Einstellung für Cookies/Autorisierung
    }
}
