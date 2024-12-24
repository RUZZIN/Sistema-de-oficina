package com.sistemaOficina.backend.entity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AcessorioVeiculo {
    private Long id;
    private Veiculo idVeiculo;
    private Acessorio idAcessorio;
    @JsonCreator
    public AcessorioVeiculo(@JsonProperty("id") Long id) {
        this.id = id;
    }
}
