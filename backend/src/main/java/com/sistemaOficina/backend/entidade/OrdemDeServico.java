package com.sistemaOficina.backend.entidade;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class OrdemDeServico {

    private int id;
    private int numero;
    private String precoFinal;
    private LocalDateTime data;
    private String status;
    private ItensPeca idItensPeca;
    private ItensServico idItensServico;
    
}
