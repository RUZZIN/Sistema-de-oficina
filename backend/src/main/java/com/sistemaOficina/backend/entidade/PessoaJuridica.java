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
    public PessoaJuridica(Long id, String nome, String logradouro, String numero, String complemento, Integer ddi1,
            Integer ddd1, Integer numero1, Integer ddi2, Integer ddd2, Integer numero2, String email, String cpf,
            String cnpj, String inscricaoEstadual, String contato, String tipoCliente, String cnpj2, String razaoSocial,
            String contato2) {
        super(id, nome, logradouro, numero, complemento, ddi1, ddd1, numero1, ddi2, ddd2, numero2, email, cpf, cnpj,
                inscricaoEstadual, contato, tipoCliente);
        cnpj = cnpj2;
        this.razaoSocial = razaoSocial;
        contato = contato2;
    }


}

