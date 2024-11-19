package com.sistemaOficina.backend.entidade;
import lombok.Data;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Servico {
    private Long id;
    private String nome;
    private String descricao;
    private double valor;
    private int duracao;

}
