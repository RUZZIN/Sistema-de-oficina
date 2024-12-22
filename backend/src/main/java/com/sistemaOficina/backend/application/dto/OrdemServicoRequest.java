package com.sistemaOficina.backend.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import com.sistemaOficina.backend.core.entity.*;

@Data
@AllArgsConstructor
public class OrdemServicoRequest {
    private String placaVeiculo;
    private Long idCliente;
    private String status;
    private List<ItensPeca> itensPeca;
    private List<ItensServico> itensServico; 
}