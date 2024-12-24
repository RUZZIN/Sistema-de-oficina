package com.sistemaOficina.backend.controller;

import com.sistemaOficina.backend.entity.Pecas;
import com.sistemaOficina.backend.infrastructure.repository.PecasRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @Operation(summary = "Listar todas as peças", 
               description = "Requisição para listar todas as peças. Requisição exige um Bearer Token.",
               security = @SecurityRequirement(name = "security"),
               responses = {
                   @ApiResponse(responseCode = "200", description = "Lista com todas as peças cadastradas"),
                   @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso")
               })
    public ResponseEntity<List<Pecas>> listarTodas() {
        List<Pecas> pecas = pecasRepository.buscarTodos();
        return ResponseEntity.ok(pecas);
    }

    // Buscar peça por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar peça por ID", 
               description = "Requisição para buscar uma peça pelo ID. Requisição exige um Bearer Token.",
               security = @SecurityRequirement(name = "security"),
               responses = {
                   @ApiResponse(responseCode = "200", description = "Peça encontrada"),
                   @ApiResponse(responseCode = "404", description = "Peça não encontrada")
               })
    public ResponseEntity<Pecas> buscarPorId(@PathVariable Long id) {
        Pecas pecas = pecasRepository.buscarPorId(id);
        if (pecas == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pecas);
    }

    // Criar nova peça
    @PostMapping
    @Operation(summary = "Criar nova peça", 
               description = "Requisição para criar uma nova peça. Requisição exige um Bearer Token.",
               security = @SecurityRequirement(name = "security"))
    public ResponseEntity<Void> criarPeca(@RequestBody Pecas novaPeca) {
        pecasRepository.salvar(novaPeca);
        return ResponseEntity.ok().build();
    }

    // Atualizar uma peça existente
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar peça existente", 
               description = "Requisição para atualizar uma peça existente. Requisição exige um Bearer Token.",
               security = @SecurityRequirement(name = "security"),
               responses = {
                   @ApiResponse(responseCode = "200", description = "Peça atualizada com sucesso"),
                   @ApiResponse(responseCode = "404", description = "Peça não encontrada")
               })
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
    @Operation(summary = "Deletar uma peça", 
               description = "Requisição para deletar uma peça existente. Requisição exige um Bearer Token.",
               security = @SecurityRequirement(name = "security"),
               responses = {
                   @ApiResponse(responseCode = "204", description = "Peça deletada com sucesso"),
                   @ApiResponse(responseCode = "404", description = "Peça não encontrada")
               })
    public ResponseEntity<Void> deletarPeca(@PathVariable Long id) {
        Pecas pecaExistente = pecasRepository.buscarPorId(id);
        if (pecaExistente == null) {
            return ResponseEntity.notFound().build();
        }

        pecasRepository.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
