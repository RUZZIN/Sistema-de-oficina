package com.sistemaOficina.backend.infrastructure.repository;

import com.sistemaOficina.backend.core.entidade.*;

import java.util.List;

public interface MarcaRepository {
    void salvar(Marca marca);
    void atualizar(Marca marca);
    void deletar(Long id);
    Marca buscarPorId(Long id);
    List<Marca> buscarTodos();
}
