package com.sistemaOficina.backend.repositorio;

import java.util.List;

import com.sistemaOficina.backend.entidade.Veiculo;

public interface VeiculoRepository {
    void salvar(Veiculo veiculo);
    void atualizar(Veiculo veiculo);
    void deletar(Long id);
    Veiculo buscarPorId(Long id);
    List<Veiculo> buscarTodos();
}
