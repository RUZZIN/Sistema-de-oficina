package com.sistemaOficina.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sistemaOficina.backend.entidade.Acessorio;
import com.sistemaOficina.backend.repositorio.AcessorioRepository;
import com.sistemaOficina.backend.repositorio.AcessorioRepositoryImpl;

@Service
public class AcessorioService {

    private final AcessorioRepository acessorioRepository;

    public AcessorioService(AcessorioRepositoryImpl acessorioRepositoryImpl) {
        this.acessorioRepository = acessorioRepositoryImpl; 
    }

    public List<Acessorio> buscarTodos() {
        return acessorioRepository.buscarTodos();
    }
}
