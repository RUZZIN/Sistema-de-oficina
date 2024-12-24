package com.sistemaOficina.backend.controller;


import com.sistemaOficina.backend.entity.*;
import com.sistemaOficina.backend.infrastructure.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    @Autowired
    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @GetMapping
    public List<Marca> listarTodas() {
        return marcaService.buscarTodos();
    }

    @GetMapping("/{id}")
    public Marca buscarPorId(@PathVariable Long id) {
        return marcaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Marca não encontrada"));
    }

    @PostMapping
    public void salvar(@RequestBody Marca marca) {
        marcaService.salvar(marca);
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Long id, @RequestBody Marca marca) {
        marca.setId(id);
        marcaService.atualizar(marca);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        marcaService.deletar(id);
    }
}
