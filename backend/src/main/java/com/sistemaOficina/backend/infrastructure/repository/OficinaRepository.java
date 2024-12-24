package com.sistemaOficina.backend.infrastructure.repository;

import java.util.List;

import com.sistemaOficina.backend.entity.*;

public interface OficinaRepository {
    void salvar(Oficina oficina);
    void atualizar(Oficina oficina);
    void deletar(Long id);
    Oficina buscarPorId(Long id);
    List<Oficina> buscarTodos();
}
