package com.sistemaOficina.backend.controller;

import com.sistemaOficina.backend.entidade.Funcionario;
import com.sistemaOficina.backend.repositorio.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    public FuncionarioController(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    // Listar todos os funcionários
    @GetMapping
    public ResponseEntity<List<Funcionario>> listarTodos() {
        List<Funcionario> funcionarios = funcionarioRepository.buscarTodos();
        return ResponseEntity.ok(funcionarios);
    }

    // Buscar funcionário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarPorId(@PathVariable Long id) {
        Funcionario funcionario = funcionarioRepository.buscarPorId(id);
        if (funcionario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(funcionario);
    }

    // Criar novo funcionário
    @PostMapping
    public ResponseEntity<Void> criarFuncionario(@RequestBody Funcionario novoFuncionario) {
        funcionarioRepository.salvar(novoFuncionario);
        return ResponseEntity.ok().build();
    }

    // Atualizar funcionário existente
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarFuncionario(@PathVariable Long id, @RequestBody Funcionario funcionarioAtualizado) {
        Funcionario funcionarioExistente = funcionarioRepository.buscarPorId(id);
        if (funcionarioExistente == null) {
            return ResponseEntity.notFound().build();
        }

        funcionarioAtualizado.setId(funcionarioExistente.getId());
        funcionarioRepository.atualizar(funcionarioAtualizado);
        return ResponseEntity.ok().build();
    }

    // Deletar funcionário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFuncionario(@PathVariable Long id) {
        Funcionario funcionarioExistente = funcionarioRepository.buscarPorId(id);
        if (funcionarioExistente == null) {
            return ResponseEntity.notFound().build();
        }

        funcionarioRepository.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
