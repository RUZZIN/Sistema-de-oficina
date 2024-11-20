package com.sistemaOficina.backend.entidade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaJuridica extends Cliente {
    private String cnpj;
    private String razaoSocial;
    private String contato;

    public PessoaJuridica(Long id, String nome, String logradouro, String numero, String complemento,
                          String ddi1, String ddd1, String numero1, String ddi2, String ddd2, String numero2,
                          String email, String cnpj, String inscricaoEstadual, String contato) {
        super(id, nome, logradouro, numero, complemento, ddi1, ddd1, numero1, ddi2, ddd2, numero2, email, null, cnpj, inscricaoEstadual, contato, "PessoaJuridica");
        this.cnpj = cnpj;
        this.contato = contato;
    }
}

