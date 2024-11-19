package com.sistemaOficina.backend.entidade;
import lombok.Data;

@Data
public class Veiculo {
    
    private Long id;
    private String placa;
    private String quilomentragem;
    private String chassi;

    
}
