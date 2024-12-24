package com.sistemaOficina.backend.infrastructure.repository;

import java.util.List;

import com.sistemaOficina.backend.entity.*;

public interface OrdemServicoRepository {
    void salvar(OrdemServico ordemServico);
    void atualizar(OrdemServico ordemServico);
    int buscarQuantidadePorPecaEOrdemServico(Long idPeca, Long idOrdemServico);
    Long buscarUltimoNumeroOs();
    void deletar(Long id);
    OrdemServico buscarPorId(Long id);
    List<OrdemServico> buscarTodos();
}
