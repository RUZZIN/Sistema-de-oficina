package com.sistemaOficina.backend.repositorio;
import java.util.List;

import com.sistemaOficina.backend.entidade.Acessorio;

public interface AcessorioRepository {
    void salvar(Acessorio acessorio);
    void atualizar(Acessorio acessorio);
    void deletar(Long id);
    Acessorio buscarPorId(Long id);
    List<Acessorio> buscarTodos();
}
