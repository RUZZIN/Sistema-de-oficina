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
@NoArgsConstructor
public class PessoaFisica extends Cliente {
    private String cpf;

    public PessoaFisica(Long id, String nome, String logradouro, String numero, String complemento,
                        String ddi1, String ddd1, String numero1, String ddi2, String ddd2, String numero2,
                        String email, String cpf) {
        super(id, nome, logradouro, numero, complemento, ddi1, ddd1, numero1, ddi2, ddd2, numero2, email, null, null, null, null, "PessoaFisica");
        this.cpf = cpf;
    }
}