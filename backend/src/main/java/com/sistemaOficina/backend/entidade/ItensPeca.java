package com.sistemaOficina.backend.entidade;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItensPeca {
    
    private    int id;
    private int quantidade;
    private double valorTotal;
    private Pecas idPeca;
}
