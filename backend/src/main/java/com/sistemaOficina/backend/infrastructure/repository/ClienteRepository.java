package com.sistemaOficina.backend.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.sistemaOficina.backend.entity.*;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteRepository {

    private final List<Cliente> clientes = new ArrayList<>();
    private Long idCounter = 1L;

    // Salvar ou atualizar cliente
    public void salvar(Cliente cliente) {
        if (cliente.getId() == null) {
            cliente.setId(idCounter++);
            clientes.add(cliente);
        } else {
            Cliente clienteExistente = buscarPorId(cliente.getId());
            if (clienteExistente != null) {
                clientes.remove(clienteExistente);
                clientes.add(cliente);
            }
        }
    }

    // Buscar todos os clientes
    public List<Cliente> buscarTodos() {
        return clientes;
    }

    // Buscar cliente por ID
    public Cliente buscarPorId(Long id) {
        return clientes.stream().filter(cliente -> cliente.getId().equals(id)).findFirst().orElse(null);
    }

    // Deletar cliente por ID
    public void deletar(Long id) {
        Cliente cliente = buscarPorId(id);
        if (cliente != null) {
            clientes.remove(cliente);
        }
    }
}
