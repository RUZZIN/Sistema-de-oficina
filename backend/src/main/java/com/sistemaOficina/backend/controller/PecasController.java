package com.sistemaOficina.backend.controller;

import com.sistemaOficina.backend.entidade.Pecas;
import com.sistemaOficina.backend.repositorio.PecasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pecas")
public class PecasController {

    private final PecasRepository pecasRepository;

    @Autowired
    public PecasController(PecasRepository pecasRepository) {
        this.pecasRepository = pecasRepository;
    }

    // Listar todas as peças
    @GetMapping
    public ResponseEntity<List<Pecas>> listarTodas() {
        List<Pecas> pecas = pecasRepository.buscarTodos();
        return ResponseEntity.ok(pecas);
    }

    // Buscar peça por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pecas> buscarPorId(@PathVariable Long id) {
        Pecas pecas = pecasRepository.buscarPorId(id);
        if (pecas == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pecas);
    }

    // Criar nova peça
    @PostMapping
    public ResponseEntity<Void> criarPeca(@RequestBody Pecas novaPeca) {
        pecasRepository.salvar(novaPeca);
        return ResponseEntity.ok().build();
    }

    // Atualizar uma peça existente
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarPeca(@PathVariable Long id, @RequestBody Pecas pecaAtualizada) {
        Pecas pecaExistente = pecasRepository.buscarPorId(id);
        if (pecaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        pecaAtualizada.setId(pecaExistente.getId());
        pecasRepository.atualizar(pecaAtualizada);
        return ResponseEntity.ok().build();
    }

    // Deletar uma peça
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPeca(@PathVariable Long id) {
        Pecas pecaExistente = pecasRepository.buscarPorId(id);
        if (pecaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        pecasRepository.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
