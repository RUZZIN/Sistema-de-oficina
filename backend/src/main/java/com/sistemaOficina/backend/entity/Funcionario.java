package com.sistemaOficina.backend.entity;
import lombok.Data;

import java.time.LocalDate;

import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Funcionario {
    
    public Funcionario(Long id) {
        this.id = id;
    }
    private Long id;
    private String nome;
    private String salario;
    private LocalDate dataNascimento;
    private LocalDate dataAdmissao;
    private LocalDate dataDemissao;
    private String cargo;
    private String endereco;
    private String telefone;
}
