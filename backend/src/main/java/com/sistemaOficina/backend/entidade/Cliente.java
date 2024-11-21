package com.sistemaOficina.backend.entidade;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
public abstract class Cliente {

    private Long id; // Chave primária
    private String nome; // Não nulo
    private String logradouro; // Não nulo
    private String numero; // Não nulo
    private String complemento; // Não nulo

    // Alterados para Integer, compatível com a tabela SQL
    private Integer ddi1; // Não nulo
    private Integer ddd1; // Não nulo
    private Integer numero1; // Não nulo

    private Integer ddi2; // Pode ser nulo
    private Integer ddd2; // Pode ser nulo
    private Integer numero2; // Pode ser nulo

    private String email; // Não nulo, único
    private String cpf; // Único, pode ser nulo
    private String cnpj; // Único, pode ser nulo
    private String inscricaoEstadual; // Único, pode ser nulo
    private String contato; // Não nulo

    private String tipoCliente; // PessoaFisica ou PessoaJuridica
}
