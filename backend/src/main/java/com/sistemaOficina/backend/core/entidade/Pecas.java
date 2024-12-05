package com.sistemaOficina.backend.core.entidade;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Pecas {
    private Long id;
    private String codigo;
    private String nome;
    private double precoUnitario;
    private int quantidade;

    public Pecas(Long id) {
        this.id = id;
    }

}
