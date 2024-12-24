package com.sistemaOficina.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    private Long id;
    private String nome;
    private String logradouro;
    private String numero;
    private String complemento;
    private Integer ddi1;
    private Integer ddd1;
    private Integer numero1;
    private Integer ddi2;
    private Integer ddd2;
    private Integer numero2;
    private String email;
    private String cpf; // Para Pessoa Física
    private String cnpj; // Para Pessoa Jurídica
    private String inscricaoEstadual; // Para Pessoa Jurídica
    private String contato; // Para Pessoa Jurídica
    private String tipoCliente; // Para identificar se é Pessoa Física ou Jurídica
    
}
