package com.sistemaOficina.backend.infrastructure.repository;
import java.util.List;

import com.sistemaOficina.backend.entity.*;

public interface ItensPecaRepository {
    void salvar(ItensPeca itensPeca);
    void atualizar(ItensPeca itensPeca);
    void deletar(int id);
    ItensPeca buscarPorId(int id);
    List<ItensPeca> buscarTodos();
}
