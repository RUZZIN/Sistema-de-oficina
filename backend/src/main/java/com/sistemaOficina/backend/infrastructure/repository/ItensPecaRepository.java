package com.sistemaOficina.backend.infrastructure.repository;
import com.sistemaOficina.backend.core.entidade.*;

import java.util.List;

public interface ItensPecaRepository {
    void salvar(ItensPeca itensPeca);
    void atualizar(ItensPeca itensPeca);
    void deletar(int id);
    ItensPeca buscarPorId(int id);
    List<ItensPeca> buscarTodos();
}
