package com.sistemaOficina.backend.resquest;

import com.sistemaOficina.backend.entidade.ItensPeca;
import com.sistemaOficina.backend.entidade.ItensServico;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrdemServicoRequest {
    private String placaVeiculo;
    private List<ItensPeca> itensPeca;   // Lista de itens de peças
    private List<ItensServico> itensServico; // Lista de itens de serviços
}