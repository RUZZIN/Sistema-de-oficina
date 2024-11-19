package com.sistemaOficina.backend.repositorio;
import java.util.List;

import com.sistemaOficina.backend.entidade.Cliente;

public interface ClienteRepository {
    void salvar(Cliente cliente);
    void atualizar(Cliente cliente);
    void deletar(Long id);
    Cliente buscarPorId(Long id);
    List<Cliente> buscarTodos();
}
