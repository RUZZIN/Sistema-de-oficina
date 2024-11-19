package com.sistemaOficina.backend.entidade;

import lombok.Data;


public class PessoaJuridica extends Cliente {

    public PessoaJuridica() {
        super();
    }
    
    private String cnpj;
    private String razaoSocial;
    private String contato;
}
