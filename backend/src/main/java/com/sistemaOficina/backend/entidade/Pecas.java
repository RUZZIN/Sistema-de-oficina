package com.sistemaOficina.backend.entidade;

import lombok.Data;

@Data
public class Pecas {

    private int id;
    private String codigo;
    private String nome;
    private double precoUnitario;
    private int quantidade;
    
}
