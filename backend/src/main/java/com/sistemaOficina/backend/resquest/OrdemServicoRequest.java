package com.sistemaOficina.backend.resquest;

import com.sistemaOficina.backend.entidade.Cliente;
import com.sistemaOficina.backend.entidade.ItensPeca;
import com.sistemaOficina.backend.entidade.ItensServico;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrdemServicoRequest {
    private String placaVeiculo;
    private Long idCliente;
    private String status;
    private List<ItensPeca> itensPeca;
    private List<ItensServico> itensServico; 
}