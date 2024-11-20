package com.sistemaOficina.backend.entidade;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Veiculo {
//Veiculo(placa, quilometragem, chassi, patrimonio, ano_modelo, ano_fabricacao, id_modelo) id_modelo referencia Modelo(id)

    private Long id;
    private String placa;
    private int quilometragem;
    private String chassi;
    private String patrimonio;
    private int anoModelo;
    private int anoFabricacao;
    private Modelo idModelo;
    private AcessorioVeiculo idAcessorioVeiculo;
    private OrdemDeServico  idOrdemDeServico;
    private Propriedade idPropriedade;

}
