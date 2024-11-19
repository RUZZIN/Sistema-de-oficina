package com.sistemaOficina.backend.repositorio;
import java.util.List;

import com.sistemaOficina.backend.entidade.Funcionario;

public interface FuncionarioRepository {
    void salvar(Funcionario funcionario);
    void atualizar(Funcionario funcionario);
    void deletar(Long id);
    Funcionario buscarPorId(Long id);
    List<Funcionario> buscarTodos();
}
