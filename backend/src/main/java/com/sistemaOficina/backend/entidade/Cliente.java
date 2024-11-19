package com.sistemaOficina.backend.entidade;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    private Long id;
    private String nome;
    private String endereco;
    private String telefone;
    private String email;


    
}
