package com.sistemaOficina.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Configura o CORS para aceitar requisições de qualquer origem
        registry.addMapping("/**") // Permitindo CORS para todos os endpoints
                .allowedOrigins("http://localhost:4200") // Adicione o URL da sua aplicação Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                .allowedHeaders("*") // Todos os headers permitidos
                .allowCredentials(true); // Permite envio de cookies
    }
}
