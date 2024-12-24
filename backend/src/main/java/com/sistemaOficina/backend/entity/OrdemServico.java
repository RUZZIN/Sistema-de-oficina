package com.sistemaOficina.backend.entity;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class OrdemServico {

    public OrdemServico(long long1) {
        
    }
    private Long numero; 
    private LocalDate data; 
    private double precoFinal;
    private String status;
    private Veiculo placaVeiculo; 
    private Cliente cliente;


}