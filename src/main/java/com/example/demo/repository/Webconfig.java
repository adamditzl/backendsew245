package com.example.demo.repository;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class Webconfig implements WebMvcConfigurer {


//cors fehler
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/api/**") // Allow CORS for API endpoints
                    .allowedOrigins("http://localhost:5173") // Allow requests from your Vue.js frontend
                    .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow specified methods
                    .allowCredentials(true); // Allow credentials if needed
        }
    }


