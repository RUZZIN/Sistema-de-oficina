package com.sistemaOficina.backend.controller;

import com.sistemaOficina.backend.entidade.Funcionario;
import com.sistemaOficina.backend.entidade.ItensPeca;
import com.sistemaOficina.backend.entidade.ItensServico;
import com.sistemaOficina.backend.entidade.OrdemServico;
import com.sistemaOficina.backend.entidade.Pecas;
import com.sistemaOficina.backend.entidade.Servico;
import com.sistemaOficina.backend.repositorio.FuncionarioRepository;
import com.sistemaOficina.backend.repositorio.ItensPecaRepositoryImpl;
import com.sistemaOficina.backend.repositorio.ItensServicoRepositoryImpl;
import com.sistemaOficina.backend.repositorio.OrdemServicoRepository;
import com.sistemaOficina.backend.repositorio.PecasRepository;
import com.sistemaOficina.backend.repositorio.ServicoRepository;
import com.sistemaOficina.backend.resquest.OrdemServicoRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import java.util.List;

@RestController
@RequestMapping("/api/ordemServico")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ItensPecaRepositoryImpl itensPecaRepository;

    @Autowired
    private ItensServicoRepositoryImpl itensServicoRepositoryImpl;

    @Autowired
    private PecasRepository pecasRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void salvar(@RequestBody OrdemServicoRequest request) {

        double precoTotal = 0;
        Long numero = ordemServicoRepository.buscarTodos().size() + 1L;

        // Buscar todos os itens de peça e serviço
        for (ItensPeca itensPecaRequest : request.getItensPeca()) {
            Long idPeca = itensPecaRequest.getIdPeca().getId();
            Pecas peca = pecasRepository.buscarPorId(idPeca); // Buscar peça pelo ID

            double precoPecaTotal = itensPecaRequest.getQuantidade() * peca.getPrecoUnitario();
            precoTotal += precoPecaTotal; // Acumula o preço total das peças

            // Criar e salvar o item de peça
            ItensPeca itensPeca = new ItensPeca(
                    0,
                    precoPecaTotal,
                    itensPecaRequest.getQuantidade(),
                    ordemServicoRepository.buscarPorId(numero),
                    peca);
            itensPecaRepository.salvar(itensPeca);
        }

        for (ItensServico itensServicoRequest : request.getItensServico()) {
            Long idServico = itensServicoRequest.getIdServico().getId();
            Long idFuncionario = itensServicoRequest.getIdFuncionario().getId();

            Servico servico = servicoRepository.buscarPorId(idServico); // Buscar serviço pelo ID
            Funcionario funcionario = funcionarioRepository.buscarPorId(idFuncionario); // Buscar funcionário pelo ID

            double precoServicoTotal = itensServicoRequest.getQuantidade() * servico.getPrecoUnitario();
            precoTotal += precoServicoTotal; // Acumula o preço total dos serviços

            // Criar e salvar o item de serviço
            ItensServico itensServico = new ItensServico(
                    0,
                    itensServicoRequest.getHorarioInicio(),
                    itensServicoRequest.getHorarioFim(),
                    itensServicoRequest.getQuantidade(),
                    precoServicoTotal,
                    funcionario,
                    servico,
                    ordemServicoRepository.buscarPorId(numero));
            itensServicoRepositoryImpl.salvar(itensServico);
        }

        OrdemServico ordemServico = new OrdemServico(
                numero,
                LocalDate.now(),
                precoTotal,
                "Execução",
                request.getPlacaVeiculo());

        ordemServicoRepository.salvar(ordemServico);
    }

    @GetMapping("/{id}")
    public OrdemServico buscarPorId(@PathVariable Long id) {
        OrdemServico ordemServico = ordemServicoRepository.buscarPorId(id);
        if (ordemServico == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ordem de serviço não encontrada");
        }
        return ordemServico;
    }

    @GetMapping
    public List<OrdemServico> buscarTodos() {
        return ordemServicoRepository.buscarTodos();
    }
}
