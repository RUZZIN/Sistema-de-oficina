package com.sistemaOficina.backend.controller;

import com.sistemaOficina.backend.entidade.Funcionario;
import com.sistemaOficina.backend.entidade.ItensPeca;
import com.sistemaOficina.backend.entidade.ItensServico;
import com.sistemaOficina.backend.entidade.OrdemServico;
import com.sistemaOficina.backend.entidade.Pecas;
import com.sistemaOficina.backend.entidade.Servico;
import com.sistemaOficina.backend.entidade.Veiculo;
import com.sistemaOficina.backend.repositorio.FuncionarioRepository;
import com.sistemaOficina.backend.repositorio.ItensPecaRepositoryImpl;
import com.sistemaOficina.backend.repositorio.ItensServicoRepositoryImpl;
import com.sistemaOficina.backend.repositorio.OrdemServicoRepository;
import com.sistemaOficina.backend.repositorio.OrdemServicoRepositoryImpl;
import com.sistemaOficina.backend.repositorio.PecasRepository;
import com.sistemaOficina.backend.repositorio.ServicoRepository;
import com.sistemaOficina.backend.repositorio.VeiculoRepository;
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
    private OrdemServicoRepositoryImpl ordemServicoRepositoryImpl;

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

    @Autowired
    private VeiculoRepository veiculoRepository;

   
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void salvar(@RequestBody OrdemServicoRequest request) {
        double precoTotal = 0;
        
        // Calcular o número da ordem de serviço baseado no último número registrado
        Long ultimoNumeroOs = ordemServicoRepositoryImpl.buscarUltimoNumeroOs();
        Long numero = (ultimoNumeroOs == null) ? 1L : ultimoNumeroOs + 1;
        
        // Criar a ordem de serviço com preço total 0 (temporário)
        Veiculo veiculo = veiculoRepository.buscarPorPlaca(request.getPlacaVeiculo());
        OrdemServico ordemServico = new OrdemServico(
                numero,
                LocalDate.now(),
                0, // Preço total inicial
                request.getStatus(), // Status inicial
                veiculo);
    
        // Salvar a ordem de serviço primeiro
        ordemServicoRepositoryImpl.salvar(ordemServico);
        
        // Garantir que foi salvo com sucesso e com o numero gerado
        if (ordemServico.getNumero() == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao salvar a ordem de serviço");
        }
    
        // Processar itens de peça
        for (ItensPeca itensPecaRequest : request.getItensPeca()) {
            Long idPeca = itensPecaRequest.getIdPeca().getId();
            Pecas peca = pecasRepository.buscarPorId(idPeca);
    
            int quantidadeUsadaPeca = itensPecaRequest.getQuantidade();
            int quantidadeUsadaPecaEstoque = peca.getQuantidade();
    
            if (quantidadeUsadaPeca > quantidadeUsadaPecaEstoque) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade de peças insuficiente");
            }
    
            peca.setQuantidade(quantidadeUsadaPecaEstoque - quantidadeUsadaPeca);
            pecasRepository.atualizar(peca);
    
            double precoPecaTotal = itensPecaRequest.getQuantidade() * peca.getPrecoUnitario();
            precoTotal += precoPecaTotal;
    
            // Criar e salvar o item de peça
            ItensPeca itensPeca = new ItensPeca(
                    0,
                    precoPecaTotal,
                    itensPecaRequest.getQuantidade(),
                    ordemServico,
                    peca);
            itensPecaRepository.salvar(itensPeca);
        }
    
        // Processar itens de serviço
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
                    ordemServico);
            itensServicoRepositoryImpl.salvar(itensServico);
        }
    
        // Atualizar o preço final da ordem de serviço
        ordemServico.setPrecoFinal(precoTotal);
        ordemServicoRepository.atualizar(ordemServico);
    }
    

    @GetMapping("/{id}")
    public OrdemServico buscarPorId(@PathVariable Long id) {
        OrdemServico ordemServico = ordemServicoRepository.buscarPorId(id);
        if (ordemServico == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ordem de serviço não encontrada");
        }
        return ordemServico;
    }

    @GetMapping("/{numeroOs}/itensPeca")
    public List<ItensPeca> buscarItensPecaPorNumeroOs(@PathVariable Long numeroOs) {
        List<ItensPeca> itensPeca = itensPecaRepository.buscarPorNumeroOs(numeroOs);
        if (itensPeca.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Nenhum item de peça encontrado para esta ordem de serviço");
        }
        return itensPeca;
    }

    @GetMapping("/{numeroOs}/itensServico")
    public List<ItensServico> buscarItensServicoPorNumeroOs(@PathVariable Long numeroOs) {
        List<ItensServico> itensServico = itensServicoRepositoryImpl.buscarPorNumeroOs(numeroOs);
        if (itensServico.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Nenhum item de serviço encontrado para esta ordem de serviço");
        }
        return itensServico;
    }

    @GetMapping
    public List<OrdemServico> buscarTodos() {
        return ordemServicoRepository.buscarTodos();
    }
}
