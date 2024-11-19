package com.sistemaOficina.backend.entidade;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Veiculo {
    
    private Long id;
    private String placa;
    private String quilomentragem;
    private String chassi;
    private Acessorio acessorio;
    
}
