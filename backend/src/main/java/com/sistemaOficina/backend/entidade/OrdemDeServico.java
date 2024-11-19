package com.sistemaOficina.backend.entidade;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrdemDeServico {

    private int id;
    private int numero;
    private String precoFinal;
    private LocalDateTime data;
    private String status;

    
}
