package com.sistemaOficina.backend.core.entity;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Marca {
    
    public Marca(int id) {
        this.id = (long) id;
    }
    private Long id;
    private String nome;

}
