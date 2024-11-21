package com.sistemaOficina.backend.entidade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class PessoaFisica extends Cliente {
    private String cpf;

    public PessoaFisica() {
    }

    public PessoaFisica(Long id, String nome, String logradouro, String numero, String complemento, Integer ddi1,
            Integer ddd1, Integer numero1, Integer ddi2, Integer ddd2, Integer numero2, String email, String cpf,
            String cnpj, String inscricaoEstadual, String contato, String tipoCliente) {
        super(id, nome, logradouro, numero, complemento, ddi1, ddd1, numero1, ddi2, ddd2, numero2, email, cpf, cnpj,
                inscricaoEstadual, contato, tipoCliente);
    }

  
}