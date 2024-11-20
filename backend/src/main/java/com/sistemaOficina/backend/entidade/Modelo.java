package com.sistemaOficina.backend.entidade;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Modelo {
    private int id;
    private String nome;
    private Marca idMarca;
}
