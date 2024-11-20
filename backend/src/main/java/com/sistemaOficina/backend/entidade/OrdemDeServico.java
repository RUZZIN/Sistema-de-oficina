package com.sistemaOficina.backend.entidade;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class OrdemDeServico {

    public OrdemDeServico(long long1) {
        //TODO Auto-generated constructor stub
    }
    private int numero;
    private String precoFinal;
    private LocalDateTime data;
    private String status;
    private ItensPeca idItensPeca;
    private ItensServico idItensServico;
    private Veiculo idVeiculo;
}
