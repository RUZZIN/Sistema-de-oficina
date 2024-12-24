package com.sistemaOficina.backend.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaOficina.backend.infrastructure.persistence.homeImpl;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final homeImpl homeRepository;

    public DashboardController(homeImpl homeRepository) {
        this.homeRepository = homeRepository;
    }

    /**
     * Endpoint para obter os dados do dashboard
     */
    @GetMapping
    public Map<String, Object> getDashboardData() {
        Map<String, Object> dashboardData = new HashMap<>();

        dashboardData.put("totalOrdensDeServico", homeRepository.getTotalOrdensDeServico());
        dashboardData.put("receitaTotal", homeRepository.getReceitaTotal());
        dashboardData.put("funcionarioComMaisServicos", homeRepository.getFuncionarioComMaisServicos());
        dashboardData.put("pecaMaisVendida", homeRepository.getPecaMaisVendida());
        dashboardData.put("servicoMaisSolicitado", homeRepository.getServicoMaisSolicitado());
        dashboardData.put("clienteComMaisOrdensDeServico", homeRepository.getClienteComMaisOrdensDeServico());
        dashboardData.put("veiculoMaisAtendido", homeRepository.getVeiculoMaisAtendido());

        return dashboardData;
    }
}
