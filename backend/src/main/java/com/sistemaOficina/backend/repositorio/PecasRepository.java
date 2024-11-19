package com.sistemaOficina.backend.repositorio;

import java.util.List;

import com.sistemaOficina.backend.entidade.Pecas;

public interface PecasRepository {
    void salvar(Pecas pecas);
    void atualizar(Pecas pecas);
    void deletar(Long id);
    Pecas buscarPorId(Long id);
    List<Pecas> buscarTodos();
}
