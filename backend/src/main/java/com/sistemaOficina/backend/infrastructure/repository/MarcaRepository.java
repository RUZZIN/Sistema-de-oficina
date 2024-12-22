package com.sistemaOficina.backend.infrastructure.repository;

import java.util.List;

import com.sistemaOficina.backend.core.entity.*;

public interface MarcaRepository {
    void salvar(Marca marca);
    void atualizar(Marca marca);
    void deletar(Long id);
    Marca buscarPorId(Long id);
    List<Marca> buscarTodos();
}
