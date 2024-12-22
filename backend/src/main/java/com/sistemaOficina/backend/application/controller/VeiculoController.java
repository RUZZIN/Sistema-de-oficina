package com.sistemaOficina.backend.application.controller;


import com.sistemaOficina.backend.core.entity.*;
import com.sistemaOficina.backend.infrastructure.repository.VeiculoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    private final VeiculoRepository veiculoRepository;

    public VeiculoController(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    // Método para salvar um veículo (POST)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) 
    public void salvar(@RequestBody Veiculo veiculo) {
        veiculoRepository.salvar(veiculo);
    }

    // Método para atualizar um veículo (PUT)
    @PutMapping("/{placa}")
    public void atualizar(@PathVariable String placa, @RequestBody Veiculo veiculo) {
    	System.out.println("veiculo atualizar: "+veiculo.toString());
    	System.out.println("placa: "+placa);
        veiculoRepository.atualizar(placa,veiculo);
    }

    // Método para deletar um veículo (DELETE)
    @DeleteMapping("/{placa}")
    public void deletar(@PathVariable String placa) {
        veiculoRepository.deletar(placa);
    }

    // Método para buscar um veículo por placa (GET)
    @GetMapping("/{placa}")
    public Veiculo buscarPorPlaca(@PathVariable String placa) {
        Veiculo veiculo = veiculoRepository.buscarPorPlaca(placa);
        if (veiculo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado");
        }
        return veiculo; // Retorna o veículo encontrado
    }

    // Método para buscar todos os veículos (GET)
    @GetMapping
    public List<Veiculo> buscarTodos() {
        return veiculoRepository.buscarTodos(); // Retorna uma lista de todos os veículos
    }
}
