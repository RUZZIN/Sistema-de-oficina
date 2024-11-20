package com.sistemaOficina.backend.entidade;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Modelo {
    public Modelo(long long1) {
        //TODO Auto-generated constructor stub
    }
    private int id;
    private String nome;
    private Marca idMarca;
}
