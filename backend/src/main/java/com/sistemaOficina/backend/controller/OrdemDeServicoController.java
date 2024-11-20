package com.sistemaOficina.backend.controller;

import com.sistemaOficina.backend.entidade.OrdemDeServico;
import com.sistemaOficina.backend.repositorio.OrdemDeServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordemdeservico")
public class OrdemDeServicoController {

    @Autowired
    private OrdemDeServicoRepository ordemDeServicoRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody OrdemDeServico ordemDeServico) {
        ordemDeServicoRepository.salvar(ordemDeServico);
    }

    @GetMapping("/{id}")
    public OrdemDeServico buscarPorId(@PathVariable Long id) {
        return ordemDeServicoRepository.buscarPorId(id);
    }

    @GetMapping
    public List<OrdemDeServico> buscarTodos() {
        return ordemDeServicoRepository.buscarTodos();
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable Long id, @RequestBody OrdemDeServico ordemDeServico) {
        ordemDeServicoRepository.atualizar(ordemDeServico);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        ordemDeServicoRepository.deletar(id);
    }
}
