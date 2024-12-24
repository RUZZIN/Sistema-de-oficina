package com.sistemaOficina.backend.controller;

import com.sistemaOficina.backend.entity.*;
import com.sistemaOficina.backend.infrastructure.persistence.ClienteRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepositoryImpl clienteRepository;

    // Cadastrar um novo cliente
    @PostMapping
    public ResponseEntity<String> salvar(@RequestBody Cliente cliente) {
        if (cliente.getTipoCliente() == null || cliente.getTipoCliente().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de cliente é obrigatório.");
        }
        if ("PessoaFisica".equals(cliente.getTipoCliente()) && (cliente.getCpf() == null || cliente.getCpf().isEmpty())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF é obrigatório para Pessoa Física.");
        }
        if ("PessoaJuridica".equals(cliente.getTipoCliente()) && (cliente.getCnpj() == null || cliente.getCnpj().isEmpty())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CNPJ é obrigatório para Pessoa Jurídica.");
        }

        clienteRepository.salvar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente criado com sucesso.");
    }

    // Buscar todos os clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> buscarTodos() {
        List<Cliente> clientes = clienteRepository.buscarTodos();
        return ResponseEntity.ok(clientes);
    }

    // Buscar cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        Cliente cliente = clienteRepository.buscarPorId(id);
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(cliente);
    }

    // Atualizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente clienteExistente = clienteRepository.buscarPorId(id);
        if (clienteExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
        }

        cliente.setId(id);
        clienteRepository.salvar(cliente);
        return ResponseEntity.ok("Cliente atualizado com sucesso.");
    }

    // Deletar cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        Cliente clienteExistente = clienteRepository.buscarPorId(id);
        if (clienteExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
        }

        clienteRepository.deletar(id);
        return ResponseEntity.ok("Cliente deletado com sucesso.");
    }
}
