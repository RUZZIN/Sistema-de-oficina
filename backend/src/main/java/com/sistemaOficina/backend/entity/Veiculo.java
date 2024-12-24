package com.sistemaOficina.backend.entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Veiculo {
    private String placa;
    private int quilometragem;
    private String chassi;
    private String patrimonio;
    private int anoModelo;
    private int anoFabricacao;
    private Modelo idModelo;
}
