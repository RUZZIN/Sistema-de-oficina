package com.sistemaOficina.backend.entidade;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Funcionario {
    
    public Funcionario(Long id) {
        this.id = id;
    }
    private Long id;
    private String nome;
    
}
