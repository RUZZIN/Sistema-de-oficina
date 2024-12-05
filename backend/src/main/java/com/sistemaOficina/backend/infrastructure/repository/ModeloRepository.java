package com.sistemaOficina.backend.infrastructure.repository;

import java.util.List;
import com.sistemaOficina.backend.core.entidade.*;

public interface ModeloRepository {
    void salvar(Modelo modelo);
    void atualizar(Modelo modelo);
    void deletar(Long id);
    Modelo buscarPorId(Long id);
    List<Modelo> buscarTodos();
}
