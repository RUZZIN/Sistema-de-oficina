package com.sistemaOficina.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor // Required by JPA
@Entity // Marks this class as a JPA Entity
public class Marca {

    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configures auto-increment for the primary key
    private Long id;

    private String nome;

    // Additional constructor if needed
    public Marca(int id) {
        this.id = (long) id;
    }
}
