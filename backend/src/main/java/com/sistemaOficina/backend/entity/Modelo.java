package com.sistemaOficina.backend.entity;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Modelo {
    private Long id;

    public Modelo(Long id) {
        this.id = id;
    }

    private String nome;
    private Marca idMarca;

    public void setLong(Long id2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setLong'");
    }
}
