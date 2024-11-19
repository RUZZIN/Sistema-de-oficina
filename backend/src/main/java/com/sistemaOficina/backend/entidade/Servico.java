package com.sistemaOficina.backend.entidade;
import lombok.Data;

@Data
public class Servico {
    private Long id;
    private String nome;
    private String descricao;
    private double valor;
    private int duracao;

}
