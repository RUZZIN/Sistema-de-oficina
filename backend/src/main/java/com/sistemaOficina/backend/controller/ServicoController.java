package com.sistemaOficina.backend.controller;


import com.sistemaOficina.backend.entity.*;
import com.sistemaOficina.backend.infrastructure.repository.ServicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private final ServicoRepository servicoRepository;

    @Autowired
    public ServicoController(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    @GetMapping
    public List<Servico> listarTodos() {
        return servicoRepository.buscarTodos();
    }

    @GetMapping("/{id}")
    public Servico buscarPorId(@PathVariable Long id) {
        return servicoRepository.buscarPorId(id);
    }

    @PostMapping
    public void salvar(@RequestBody Servico servico) {
        servicoRepository.salvar(servico);
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Long id, @RequestBody Servico servico) {
        servico.setId(id);
        servicoRepository.atualizar(servico);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        servicoRepository.deletar(id);
    }
}
