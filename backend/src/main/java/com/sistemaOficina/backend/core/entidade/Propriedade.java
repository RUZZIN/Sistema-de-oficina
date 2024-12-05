package com.sistemaOficina.backend.core.entidade;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Propriedade {
    private Long id;
    private String dataInicio;
    private String dataTermino;
    private Cliente idCliente;
    private Veiculo idVeiculo;

}
