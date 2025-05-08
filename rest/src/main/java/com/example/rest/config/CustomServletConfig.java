package com.example.rest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomServletConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) { // 메서드 이름 수정
        registry.addMapping("/**")
                .allowedOrigins("*") // 어디서 뭐가 와도
                .allowedMethods("HEAD", "GET", "POST", "DELETE", "PUT")
                .maxAge(300)
                .allowedHeaders("Authorization", "Cache-Control", "Content-Type");
    }
}