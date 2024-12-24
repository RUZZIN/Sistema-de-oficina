package com.sistemaOficina.backend.infrastructure.repository;
import java.util.List;

import com.sistemaOficina.backend.entity.Funcionario;


public interface FuncionarioRepository {
    void salvar(Funcionario funcionario);
    void atualizar(Funcionario funcionario);
    void deletar(Long id);
    Funcionario buscarPorId(Long id);
    List<Funcionario> buscarTodos();
}
