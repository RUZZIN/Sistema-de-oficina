package com.sistemaOficina.backend.repositorio;

import java.util.List;

import com.sistemaOficina.backend.entidade.Oficina;

public interface OficinaRepository {
    void salvar(Oficina oficina);
    void atualizar(Oficina oficina);
    void deletar(Long id);
    Oficina buscarPorId(Long id);
    List<Oficina> buscarTodos();
}
