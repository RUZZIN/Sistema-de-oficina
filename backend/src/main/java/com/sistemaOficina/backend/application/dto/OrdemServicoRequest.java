package com.sistemaOficina.backend.application.dto;

import com.sistemaOficina.backend.core.entidade.*;
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