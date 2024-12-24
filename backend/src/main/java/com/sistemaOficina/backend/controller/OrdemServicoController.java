package com.sistemaOficina.backend.controller;

import com.sistemaOficina.backend.dto.OrdemServicoRequest;
import com.sistemaOficina.backend.entity.*;
import com.sistemaOficina.backend.infrastructure.persistence.ClienteRepositoryImpl;
import com.sistemaOficina.backend.infrastructure.persistence.ItensPecaRepositoryImpl;
import com.sistemaOficina.backend.infrastructure.persistence.ItensServicoRepositoryImpl;
import com.sistemaOficina.backend.infrastructure.repository.FuncionarioRepository;
import com.sistemaOficina.backend.infrastructure.repository.OrdemServicoRepository;
import com.sistemaOficina.backend.infrastructure.repository.PecasRepository;
import com.sistemaOficina.backend.infrastructure.repository.ServicoRepository;
import com.sistemaOficina.backend.infrastructure.repository.VeiculoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/ordemServico")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ItensPecaRepositoryImpl itensPecaRepository;

    @Autowired
    private ClienteRepositoryImpl clienteRepository;

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

        // Gerar número da Ordem de Serviço
        Long ultimoNumero = ordemServicoRepository.buscarUltimoNumeroOs();
        Long numero = (ultimoNumero != null) ? ultimoNumero + 1 : 1;

        // Validar e buscar Veículo e Cliente
        Veiculo veiculo = veiculoRepository.buscarPorPlaca(request.getPlacaVeiculo());
        if (veiculo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado");
        }

        Cliente cliente = clienteRepository.buscarPorId(request.getIdCliente());
        if (cliente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }

        // Criar e salvar Ordem de Serviço
        OrdemServico ordemServico = new OrdemServico(
                numero,
                LocalDate.now(),
                0,
                request.getStatus(),
                veiculo,
                cliente
        );
        ordemServicoRepository.salvar(ordemServico);

        if (ordemServico.getNumero() == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Falha ao salvar a ordem de serviço");
        }

        // Processar Itens de Peça
        precoTotal += processarItensPeca(request.getItensPeca(), ordemServico);

        // Processar Itens de Serviço
        precoTotal += processarItensServico(request.getItensServico(), ordemServico);

        // Atualizar preço final na Ordem de Serviço
        ordemServico.setPrecoFinal(precoTotal);
        ordemServicoRepository.atualizar(ordemServico);
    }

    private double processarItensPeca(List<ItensPeca> itensPecaList, OrdemServico ordemServico) {
        double precoTotalPecas = 0;

        for (ItensPeca itensPecaRequest : itensPecaList) {
            Pecas peca = pecasRepository.buscarPorId(itensPecaRequest.getIdPeca().getId());
            int quantidadeUsada = itensPecaRequest.getQuantidade();

            if (quantidadeUsada > peca.getQuantidade()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade de peças insuficiente");
            }

            peca.setQuantidade(peca.getQuantidade() - quantidadeUsada);
            pecasRepository.atualizar(peca);

            double precoPecaTotal = quantidadeUsada * peca.getPrecoUnitario();
            precoTotalPecas += precoPecaTotal;

            ItensPeca itensPeca = new ItensPeca(
                    0,
                    precoPecaTotal,
                    quantidadeUsada,
                    ordemServico,
                    peca
            );
            itensPecaRepository.salvar(itensPeca);
        }

        return precoTotalPecas;
    }

    private double processarItensServico(List<ItensServico> itensServicoList, OrdemServico ordemServico) {
        double precoTotalServicos = 0;

        for (ItensServico itensServicoRequest : itensServicoList) {
            Servico servico = servicoRepository.buscarPorId(itensServicoRequest.getIdServico().getId());
            Funcionario funcionario = funcionarioRepository.buscarPorId(itensServicoRequest.getIdFuncionario().getId());

            double precoServicoTotal = itensServicoRequest.getQuantidade() * servico.getPrecoUnitario();
            precoTotalServicos += precoServicoTotal;

            ItensServico itensServico = new ItensServico(
                    0,
                    itensServicoRequest.getHorarioInicio(),
                    itensServicoRequest.getHorarioFim(),
                    itensServicoRequest.getQuantidade(),
                    precoServicoTotal,
                    funcionario,
                    servico,
                    ordemServico
            );
            itensServicoRepositoryImpl.salvar(itensServico);
        }

        return precoTotalServicos;
    }

    @PutMapping("/{numero}")
    @Transactional
    public void salvarAlteracao(@PathVariable Long numero, @RequestBody OrdemServicoRequest request) {
        double precoTotal = 0;

        // Verificar se a Ordem de Serviço existe
        OrdemServico ordemServico = ordemServicoRepository.buscarPorId(numero);
        if (ordemServico == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ordem de serviço não encontrada");
        }

        // Atualizar os detalhes da Ordem de Serviço
        Veiculo veiculo = veiculoRepository.buscarPorPlaca(request.getPlacaVeiculo());
        if (veiculo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado");
        }

        Cliente cliente = clienteRepository.buscarPorId(request.getIdCliente());
        if (cliente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }

        ordemServico.setCliente(cliente);
        ordemServico.setStatus(request.getStatus());
        ordemServico.setPlacaVeiculo(veiculo);

        // Atualizar Itens de Peças
        for (ItensPeca itensPecaRequest : request.getItensPeca()) {
            Pecas peca = pecasRepository.buscarPorId(itensPecaRequest.getIdPeca().getId());
            if (peca == null) {
                System.out.println("DEBUG: Peça não encontrada - ID: " + itensPecaRequest.getIdPeca().getId());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Peça não encontrada");
            }

            // Recuperar a quantidade previamente usada na OS (se existir)
            int quantidadeAnterior = ordemServicoRepository.buscarQuantidadePorPecaEOrdemServico(
                    ordemServico.getNumero(),
                    itensPecaRequest.getIdPeca().getId()
            );

            // Calcular a diferença real
            int diferenca = itensPecaRequest.getQuantidade() - quantidadeAnterior;

            // Verificar se há estoque suficiente para a alteração
            if (diferenca > 0 && peca.getQuantidade() < diferenca) {
                System.out.println("DEBUG: Estoque insuficiente - Quantidade em Estoque: " + peca.getQuantidade() + ", Diferença Necessária: " + diferenca);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estoque insuficiente para a peça");
            }

            // Atualizar o estoque com base na diferença
            peca.setQuantidade(peca.getQuantidade() - diferenca);
            pecasRepository.atualizar(peca);

            double precoPecaTotal = itensPecaRequest.getQuantidade() * peca.getPrecoUnitario();
            precoTotal += precoPecaTotal;

            // Atualizar ou criar Item de Peça
            ItensPeca itensPeca = itensPecaRepository.buscarPorId(itensPecaRequest.getId());
            System.out.println("DEBUG: Ordem de Serviço - Número: " + ordemServico.getNumero());

            if (itensPeca == null) {
                System.err.println("entrrou no if");
                itensPeca = new ItensPeca(
                        0,
                        precoPecaTotal,
                        itensPecaRequest.getQuantidade(),
                        ordemServico,
                        peca
                );

                itensPecaRepository.salvar(itensPeca);
            } else {
                itensPeca.setQuantidade(itensPecaRequest.getQuantidade());
                itensPeca.setPrecoTotal(precoPecaTotal);
                itensPecaRepository.atualizar(itensPeca);
            }
        }

        // Atualizar Itens de Serviços
        for (ItensServico itensServicoRequest : request.getItensServico()) {
            Servico servico = servicoRepository.buscarPorId(itensServicoRequest.getIdServico().getId());
            Funcionario funcionario = funcionarioRepository.buscarPorId(itensServicoRequest.getIdFuncionario().getId());
            if (servico == null || funcionario == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço ou Funcionário não encontrado");
            }

            double precoServicoTotal = itensServicoRequest.getQuantidade() * servico.getPrecoUnitario();
            precoTotal += precoServicoTotal;

            // Atualizar ou criar Item de Serviço
            ItensServico itensServico = itensServicoRepositoryImpl.buscarPorId(itensServicoRequest.getId());
            if (itensServico == null) {
                itensServico = new ItensServico(
                        0,
                        itensServicoRequest.getHorarioInicio(),
                        itensServicoRequest.getHorarioFim(),
                        itensServicoRequest.getQuantidade(),
                        precoServicoTotal,
                        funcionario,
                        servico,
                        ordemServico
                );
                itensServicoRepositoryImpl.salvar(itensServico);
            } else {
                itensServico.setHorarioInicio(itensServicoRequest.getHorarioInicio());
                itensServico.setHorarioFim(itensServicoRequest.getHorarioFim());
                itensServico.setQuantidade(itensServicoRequest.getQuantidade());
                itensServico.setPrecoTotal(precoServicoTotal);
                itensServicoRepositoryImpl.atualizar(itensServico);
            }
        }

        // Atualizar o preço total da Ordem de Serviço
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
            return new ArrayList<>();
        }
        return itensPeca;
    }

    @GetMapping("/{numeroOs}/itensServico")
    public List<ItensServico> buscarItensServicoPorNumeroOs(@PathVariable Long numeroOs) {
        List<ItensServico> itensServico = itensServicoRepositoryImpl.buscarPorNumeroOs(numeroOs);
        if (itensServico.isEmpty()) {
            return new ArrayList<>();
        }
        return itensServico;
    }

    @GetMapping
    public List<OrdemServico> buscarTodos() {
        return ordemServicoRepository.buscarTodos();
    }
}
