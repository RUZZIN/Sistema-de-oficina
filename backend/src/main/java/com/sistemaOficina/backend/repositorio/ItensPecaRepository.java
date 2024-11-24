package com.sistemaOficina.backend.repositorio;

import com.sistemaOficina.backend.entidade.ItensPeca;
import java.util.List;

public interface ItensPecaRepository {
    void salvar(ItensPeca itensPeca);
    void atualizar(ItensPeca itensPeca);
    void deletar(int id);
    ItensPeca buscarPorId(int id);
    List<ItensPeca> buscarTodos();
}
