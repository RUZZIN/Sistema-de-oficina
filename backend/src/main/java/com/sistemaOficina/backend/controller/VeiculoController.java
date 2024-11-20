package com.sistemaOficina.backend.controller;

import com.sistemaOficina.backend.entidade.Veiculo;
import com.sistemaOficina.backend.repositorio.VeiculoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    private final VeiculoRepository veiculoRepository;

    public VeiculoController(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody Veiculo veiculo) {
        veiculoRepository.salvar(veiculo);
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Long id, @RequestBody Veiculo veiculo) {
        veiculo.setId(id); // Garantir que o ID é o do path
        veiculoRepository.atualizar(veiculo);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        veiculoRepository.deletar(id);
    }

    @GetMapping("/{id}")
    public Veiculo buscarPorId(@PathVariable Long id) {
        return veiculoRepository.buscarPorId(id);
    }

    @GetMapping
    public List<Veiculo> buscarTodos() {
        return veiculoRepository.buscarTodos();
    }
}
