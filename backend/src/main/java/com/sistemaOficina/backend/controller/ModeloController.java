package com.sistemaOficina.backend.controller;


import com.sistemaOficina.backend.entity.*;
import com.sistemaOficina.backend.infrastructure.repository.ModeloRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modelos")
public class ModeloController {

    private final ModeloRepository modeloRepository;

    public ModeloController(ModeloRepository modeloRepository) {
        this.modeloRepository = modeloRepository;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody Modelo modelo) {
        modeloRepository.salvar(modelo);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody Modelo modelo) {
        modelo.setLong(id);
        modeloRepository.atualizar(modelo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        modeloRepository.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Modelo> buscarPorId(@PathVariable Long id) {
        Modelo modelo = modeloRepository.buscarPorId(id);
        return modelo != null ? ResponseEntity.ok(modelo) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Modelo>> buscarTodos() {
        return ResponseEntity.ok(modeloRepository.buscarTodos());
    }
}
