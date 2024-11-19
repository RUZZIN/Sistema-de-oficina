package com.sistemaOficina.backend.repositorio;

import com.sistemaOficina.backend.entidade.Marca;

import java.util.List;

public interface MarcaRepository {
    void salvar(Marca marca);
    void atualizar(Marca marca);
    void deletar(Long id);
    Marca buscarPorId(Long id);
    List<Marca> buscarTodos();
}
