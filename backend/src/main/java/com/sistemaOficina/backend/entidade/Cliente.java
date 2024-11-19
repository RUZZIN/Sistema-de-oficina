package com.sistemaOficina.backend.entidade;
import lombok.Data;

@Data
public class Cliente {

    private Long id;
    private String nome;
    private String endereco;
    private String telefone;
    private String email;
    
}
