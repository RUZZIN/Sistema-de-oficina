package com.sistemaOficina.backend.controller;

import com.sistemaOficina.backend.dto.UsuarioResponseDto;
import com.sistemaOficina.backend.entity.*;
import com.sistemaOficina.backend.exception.ErrorMessage;
import com.sistemaOficina.backend.infrastructure.repository.VeiculoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Operation(summary = "Salvar um novo veículo", 
               description = "Requisição para salvar um novo veículo. Requer autorização.",
               security = @SecurityRequirement(name = "security"))
    public void salvar(@RequestBody Veiculo veiculo) {
        veiculoRepository.salvar(veiculo);
    }

    // Método para atualizar um veículo (PUT)
    @PutMapping("/{placa}")
    @Operation(summary = "Atualizar dados de um veículo", 
               description = "Requisição para atualizar dados de um veículo existente. Requer autorização.",
               security = @SecurityRequirement(name = "security"))
    public void atualizar(@PathVariable String placa, @RequestBody Veiculo veiculo) {
        System.out.println("veiculo atualizar: " + veiculo.toString());
        System.out.println("placa: " + placa);
        veiculoRepository.atualizar(placa, veiculo);
    }

    // Método para deletar um veículo (DELETE)
    @DeleteMapping("/{placa}")
    @Operation(summary = "Deletar um veículo", 
               description = "Requisição para deletar um veículo existente. Requer autorização.",
               security = @SecurityRequirement(name = "security"))
    public void deletar(@PathVariable String placa) {
        veiculoRepository.deletar(placa);
    }

    // Método para buscar um veículo por placa (GET)
    @GetMapping("/{placa}")
    @Operation(summary = "Buscar veículo por placa", 
               description = "Requisição para buscar um veículo específico pelo número da placa.",
               security = @SecurityRequirement(name = "security"),
               responses = {
                   @ApiResponse(responseCode = "200", description = "Veículo encontrado",
                                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Veiculo.class))),
                   @ApiResponse(responseCode = "404", description = "Veículo não encontrado",
                                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
               })
    public Veiculo buscarPorPlaca(@PathVariable String placa) {
        Veiculo veiculo = veiculoRepository.buscarPorPlaca(placa);
        if (veiculo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado");
        }
        return veiculo; // Retorna o veículo encontrado
    }

    // Método para buscar todos os veículos (GET)
    @Operation(summary = "Listar todos os veículos cadastrados", 
               description = "Requisição para listar todos os veículos. Requisição exige um Bearer Token. Acesso restrito a ADMIN.",
               security = @SecurityRequirement(name = "security"),
               responses = {
                   @ApiResponse(responseCode = "200", description = "Lista com todos os veículos cadastrados",
                                content = @Content(mediaType = "application/json", 
                                                   array = @ArraySchema(schema = @Schema(implementation = Veiculo.class)))),
                   @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar este recurso",
                                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
               })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') OR hasAnyRole('CLIENTE')")
    public List<Veiculo> buscarTodos() {
        return veiculoRepository.buscarTodos(); // Retorna uma lista de todos os veículos
    }
}
