package com.sistemaOficina.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DatabaseConfig {

    @Bean
    public Connection connection() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/eng_3", // Replace with your actual DB URL
            "postgres", // Replace with your DB username
            "root"  // Replace with your DB password
        );
    }
}
