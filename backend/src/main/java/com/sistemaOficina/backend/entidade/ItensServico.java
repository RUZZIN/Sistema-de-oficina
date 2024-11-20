package com.sistemaOficina.backend.entidade;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItensServico {
    private int id;
    private String inicio;
    private String fim;
    private double precoTotal;
    private int quantidade;

    private Funcionario idFuncionario;
    private Servico idServico;    
    
}
