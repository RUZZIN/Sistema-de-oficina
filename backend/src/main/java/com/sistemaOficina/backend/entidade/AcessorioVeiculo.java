package com.sistemaOficina.backend.entidade;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AcessorioVeiculo {
    private Veiculo idVeiculo;
    private Acessorio idAcessorio;
}
