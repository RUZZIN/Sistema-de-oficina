package com.sistemaOficina.backend.infrastructure.repository;

import java.util.List;

import com.sistemaOficina.backend.entity.*;

public interface ServicoRepository {
    void salvar(Servico servico);
    void atualizar(Servico servico);
    void deletar(Long id);
    Servico buscarPorId(Long id);
    List<Servico> buscarTodos();
}
