package com.sistemaOficina.backend.entidade;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class ItensServico {
    private int id;
    
    private LocalTime horarioInicio;
    private LocalTime horarioFim; 
    private int quantidade; 
    private double precoTotal;
    private Funcionario idFuncionario; 
    private Servico idServico; 
}
