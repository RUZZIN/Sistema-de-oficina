package com.sistemaOficina.backend.infrastructure.repository;

import java.util.List;

import com.sistemaOficina.backend.entity.*;

public interface PecasRepository {
    void salvar(Pecas pecas);
    void atualizar(Pecas pecas);
    void deletar(Long id);
    Pecas buscarPorId(Long id);
    List<Pecas> buscarTodos();
}
