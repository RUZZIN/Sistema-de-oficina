package com.sistemaOficina.backend.entidade;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Pecas {

    private int id;
    private String codigo;
    private String nome;
    private double precoUnitario;
    private int quantidade;
    
    

}
