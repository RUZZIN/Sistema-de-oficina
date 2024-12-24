package com.sistemaOficina.backend.infrastructure.repository;
import java.util.List;

import com.sistemaOficina.backend.entity.*;


public interface AcessorioRepository {
    void salvar(Acessorio acessorio);
    void atualizar(Acessorio acessorio);
    void deletar(Long id);
    Acessorio buscarPorId(Long id);
    List<Acessorio> buscarTodos();
}
