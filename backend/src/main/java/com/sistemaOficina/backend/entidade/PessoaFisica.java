package com.sistemaOficina.backend.entidade;

import lombok.Data;

import lombok.Data;
import lombok.AllArgsConstructor;

public class PessoaFisica extends Cliente {

    private String cpf;

    public PessoaFisica(String cpf) {
        super();
        this.cpf = cpf;
    }
}
