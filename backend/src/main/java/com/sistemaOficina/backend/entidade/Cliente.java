package com.sistemaOficina.backend.entidade;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "tipoCliente"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = PessoaFisica.class, name = "PessoaFisica"),
    @JsonSubTypes.Type(value = PessoaJuridica.class, name = "PessoaJuridica")
})
@Data
@NoArgsConstructor
public abstract class Cliente {
    
    private Long id;
    private String nome;
    private String logradouro;
    private String numero;
    private String complemento;
    private String ddi1;
    private String ddd1;
    private String numero1;
    private String ddi2;
    private String ddd2;
    private String numero2;
    private String email;
    private String cpf;
    private String cnpj;
    private String inscricaoEstadual;
    private String contato;
    private String tipoCliente;

    public Cliente(Long id, String nome, String logradouro, String numero, String complemento, String ddi1, String ddd1,
            String numero1, String ddi2, String ddd2, String numero2, String email, String cpf, String cnpj,
            String inscricaoEstadual, String contato, String tipoCliente) {
        this.id = id;
        this.nome = nome;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.ddi1 = ddi1;
        this.ddd1 = ddd1;
        this.numero1 = numero1;
        this.ddi2 = ddi2;
        this.ddd2 = ddd2;
        this.numero2 = numero2;
        this.email = email;
        this.cpf = cpf;
        this.cnpj = cnpj;
        this.inscricaoEstadual = inscricaoEstadual;
        this.contato = contato;
        this.tipoCliente = tipoCliente;
    }


}
