package com.sistemaOficina.backend.repositorio;

import java.util.List;

import com.sistemaOficina.backend.entidade.OrdemDeServico;

public interface OrdemDeServicoRepository {
    void salvar(OrdemDeServico ordemDeServico);
    void atualizar(OrdemDeServico ordemDeServico);
    void deletar(Long id);
    OrdemDeServico buscarPorId(Long id);
    List<OrdemDeServico> buscarTodos();
}
