package com.sistemaOficina.backend.controller;

import com.sistemaOficina.backend.entidade.Marca;
import com.sistemaOficina.backend.repositorio.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    private final MarcaRepository marcaRepository;

    @Autowired
    public MarcaController(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    @GetMapping
    public List<Marca> listarTodas() {
        return marcaRepository.buscarTodos();
    }

    @GetMapping("/{id}")
    public Marca buscarPorId(@PathVariable Long id) {
        return marcaRepository.buscarPorId(id);
    }

    @PostMapping
    public void salvar(@RequestBody Marca marca) {
        marcaRepository.salvar(marca);
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Long id, @RequestBody Marca marca) {
        marca.setId(id);
        marcaRepository.atualizar(marca);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        marcaRepository.deletar(id);
    }
}
