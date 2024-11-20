package com.sistemaOficina.backend.repositorio;

import java.util.List;
import com.sistemaOficina.backend.entidade.Modelo;

public interface ModeloRepository {
    void salvar(Modelo modelo);
    void atualizar(Modelo modelo);
    void deletar(Long id);
    Modelo buscarPorId(Long id);
    List<Modelo> buscarTodos();
}
