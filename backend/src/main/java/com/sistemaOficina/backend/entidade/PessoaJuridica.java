package com.sistemaOficina.backend.entidade;

import lombok.Data;

@Data
public class PessoaJuridica  extends Cliente {
    
    private String cnpj;
    private String razaoSocial;
    private String contato;
}
