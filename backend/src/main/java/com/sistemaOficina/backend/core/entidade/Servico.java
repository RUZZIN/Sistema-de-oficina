package com.sistemaOficina.backend.core.entidade;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Servico {
    private Long id;
    private String nome;
    private double precoUnitario;

    public Servico(Long id) {
        this.id = id;
    }
}
