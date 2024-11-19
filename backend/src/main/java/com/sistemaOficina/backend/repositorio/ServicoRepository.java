package com.sistemaOficina.backend.repositorio;

import java.util.List;

import com.sistemaOficina.backend.entidade.Servico;

public interface ServicoRepository {
    void salvar(Servico servico);
    void atualizar(Servico servico);
    void deletar(Long id);
    Servico buscarPorId(Long id);
    List<Servico> buscarTodos();
}
