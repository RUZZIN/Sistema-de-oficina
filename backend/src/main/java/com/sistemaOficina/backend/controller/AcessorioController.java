package com.sistemaOficina.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sistemaOficina.backend.entity.*;

import com.sistemaOficina.backend.infrastructure.repository.AcessorioRepository;

import java.util.List;

@RestController
@RequestMapping("/api/acessorios")
public class AcessorioController {
    private  final AcessorioRepository acessorioRepository;


    public AcessorioController(AcessorioRepository acessorioRepository) {
        this.acessorioRepository = acessorioRepository;
    }

    @PostMapping
    public ResponseEntity<String> salvar(@RequestBody Acessorio acessorio) {
        acessorioRepository.salvar(acessorio);
        return ResponseEntity.ok("Acessório salvo com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody Acessorio acessorio) {
        acessorio.setId(id);
        acessorioRepository.atualizar(acessorio);
        return ResponseEntity.ok("Acessório atualizado com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        acessorioRepository.deletar(id);
        return ResponseEntity.ok("Acessório deletado com sucesso!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Acessorio> buscarPorId(@PathVariable Long id) {
        Acessorio acessorio = acessorioRepository.buscarPorId(id);
        if (acessorio != null) {
            return ResponseEntity.ok(acessorio);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Acessorio>> buscarTodos() {
        List<Acessorio> acessorios = acessorioRepository.buscarTodos();
        return ResponseEntity.ok(acessorios);
    }
}
