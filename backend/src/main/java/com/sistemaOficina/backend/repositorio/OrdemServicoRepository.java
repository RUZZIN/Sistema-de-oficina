package com.sistemaOficina.backend.repositorio;

import java.util.List;
import com.sistemaOficina.backend.entidade.OrdemServico;

public interface OrdemServicoRepository {
    void salvar(OrdemServico ordemServico);
    void atualizar(OrdemServico ordemServico);
    void deletar(Long id);
    OrdemServico buscarPorId(Long id);
    List<OrdemServico> buscarTodos();
}
