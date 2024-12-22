package com.sistemaOficina.backend.infrastructure.repository;

import java.util.List;

import com.sistemaOficina.backend.core.entity.*;

public interface VeiculoRepository {
    void salvar(Veiculo veiculo);
    void atualizar(String placa, Veiculo veiculo);
    void deletar(String id);
    Veiculo buscarPorPlaca(String placa);
    List<Veiculo> buscarTodos();
}
