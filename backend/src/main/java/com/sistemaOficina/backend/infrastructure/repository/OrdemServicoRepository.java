package com.sistemaOficina.backend.infrastructure.repository;

import java.util.List;
import com.sistemaOficina.backend.core.entidade.*;

public interface OrdemServicoRepository {
    void salvar(OrdemServico ordemServico);
    void atualizar(OrdemServico ordemServico);
    void deletar(Long id);
    OrdemServico buscarPorId(Long id);
    List<OrdemServico> buscarTodos();
}
