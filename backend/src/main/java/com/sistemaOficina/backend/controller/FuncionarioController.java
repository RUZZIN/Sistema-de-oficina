package com.sistemaOficina.backend.controller;



import com.sistemaOficina.backend.entity.*;

import com.sistemaOficina.backend.infrastructure.repository.FuncionarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioController(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    // Método para salvar um funcionário (POST)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody Funcionario funcionario) {
    	funcionario.toString();
        funcionarioRepository.salvar(funcionario);
    }

    // Método para atualizar um funcionário (PUT)
    @PutMapping("/{id}")
    public void atualizar(@PathVariable Long id, @RequestBody Funcionario funcionario) {
        Funcionario funcionarioExistente = funcionarioRepository.buscarPorId(id);
        if (funcionarioExistente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado");
        }
        funcionario.setId(funcionarioExistente.getId());
        funcionarioRepository.atualizar(funcionario);
    }

    // Método para deletar um funcionário (DELETE)
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        Funcionario funcionario = funcionarioRepository.buscarPorId(id);
        if (funcionario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado");
        }
        funcionarioRepository.deletar(id);
    }

    // Método para buscar um funcionário por ID (GET)
    @GetMapping("/{id}")
    public Funcionario buscarPorId(@PathVariable Long id) {
        Funcionario funcionario = funcionarioRepository.buscarPorId(id);
        if (funcionario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado");
        }
        return funcionario;
    }

    // Método para buscar todos os funcionários (GET)
    @GetMapping
    public List<Funcionario> buscarTodos() {
        return funcionarioRepository.buscarTodos();
    }
}
