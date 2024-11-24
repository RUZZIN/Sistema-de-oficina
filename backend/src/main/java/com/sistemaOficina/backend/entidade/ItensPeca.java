package com.sistemaOficina.backend.entidade;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItensPeca {
    private int id;
    private double precoTotal;
    private int quantidade;
    private OrdemServico numeroOs;  
    private Pecas idPeca;
}
