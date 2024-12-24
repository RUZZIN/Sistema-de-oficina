package com.sistemaOficina.backend.infrastructure.persistence;


import com.sistemaOficina.backend.entity.*;
import com.sistemaOficina.backend.infrastructure.repository.FuncionarioRepository;
import com.sistemaOficina.backend.infrastructure.repository.ItensServicoRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class ItensServicoRepositoryImpl implements ItensServicoRepository {

    private final Connection connection;

    private ServicoRepositoryImpl servicoRepositoryImpl;
    private FuncionarioRepository funcionarioRepository;
    private OrdemServicoRepositoryImpl ordemServicoRepositoryImpl;

    public ItensServicoRepositoryImpl(Connection connection, OrdemServicoRepositoryImpl ordemServicoRepositoryImpl, FuncionarioRepository funcionarioRepository, ServicoRepositoryImpl servicoRepositoryImpl) {
        this.connection = connection;
        this.funcionarioRepository = funcionarioRepository;
        this.servicoRepositoryImpl = servicoRepositoryImpl;
        this.ordemServicoRepositoryImpl = ordemServicoRepositoryImpl;

    }
    @Override
    public void salvar(ItensServico itensServico) {
        // O número da ordem de serviço é gerado na hora de salvar a ordem de serviço
        Long numeroOs = itensServico.getNumeroOs().getNumero();
    
        String sql = "INSERT INTO itens_servico (horario_inicio, horario_fim, quantidade, preco_total, id_funcionario, id_servico, numero_os) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTime(1, Time.valueOf(itensServico.getHorarioInicio()));
            stmt.setTime(2, Time.valueOf(itensServico.getHorarioFim()));
            stmt.setInt(3, itensServico.getQuantidade());
            stmt.setDouble(4, itensServico.getPrecoTotal());
            stmt.setLong(5, itensServico.getIdFuncionario().getId());
            stmt.setLong(6, itensServico.getIdServico().getId());
            stmt.setLong(7, numeroOs);  // Número da Ordem de Serviço
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    @Override
    public void atualizar(ItensServico itensServico) {
        String sql = "UPDATE itens_servico SET horario_inicio = ?, horario_fim = ?, quantidade = ?, preco_total = ?, id_funcionario = ?, id_servico = ?, numero_os = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTime(1, Time.valueOf(itensServico.getHorarioInicio()));
            stmt.setTime(2, Time.valueOf(itensServico.getHorarioFim()));
            stmt.setInt(3, itensServico.getQuantidade());
            stmt.setDouble(4, itensServico.getPrecoTotal());
            stmt.setLong(5, itensServico.getIdFuncionario().getId());
            stmt.setLong(6, itensServico.getIdServico().getId());
            stmt.setLong(7, itensServico.getNumeroOs().getNumero());  // Atualizando com numero_os
            stmt.setInt(8, itensServico.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM itens_servico WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ItensServico buscarPorId(int id) {
        String sql = "SELECT * FROM itens_servico WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ItensServico(
                    rs.getInt("id"),
                    rs.getTime("horario_inicio").toLocalTime(),
                    rs.getTime("horario_fim").toLocalTime(),
                    rs.getInt("quantidade"),
                    rs.getDouble("preco_total"),
                    funcionarioRepository.buscarPorId(rs.getLong("id_funcionario")), 
                    servicoRepositoryImpl.buscarPorId(rs.getLong("id_servico")),
                    ordemServicoRepositoryImpl.buscarPorId(rs.getLong("numero_os"))
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ItensServico> buscarTodos() {
        List<ItensServico> lista = new ArrayList<>();
        String sql = "SELECT * FROM itens_servico";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new ItensServico(
                    rs.getInt("id"),
                    rs.getTime("horario_inicio").toLocalTime(),
                    rs.getTime("horario_fim").toLocalTime(),
                    rs.getInt("quantidade"),
                    rs.getDouble("preco_total"),
                    funcionarioRepository.buscarPorId(rs.getLong("id_funcionario")), 
                    servicoRepositoryImpl.buscarPorId(rs.getLong("id_servico")),
                    ordemServicoRepositoryImpl.buscarPorId(rs.getLong("numero_os"))
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<ItensServico> buscarPorNumeroOs(long numeroOs) {
        List<ItensServico> lista = new ArrayList<>();
        String sql = "SELECT * FROM itens_servico WHERE numero_os = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, numeroOs); 
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new ItensServico(
                    rs.getInt("id"),
                    rs.getTime("horario_inicio").toLocalTime(),
                    rs.getTime("horario_fim").toLocalTime(),
                    rs.getInt("quantidade"),
                    rs.getDouble("preco_total"),
                    funcionarioRepository.buscarPorId(rs.getLong("id_funcionario")), 
                    servicoRepositoryImpl.buscarPorId(rs.getLong("id_servico")), 
                    ordemServicoRepositoryImpl.buscarPorId(rs.getLong("numero_os"))  // Incluindo numero_os na recuperação
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }


    public Optional<ItensServico> buscarPorOrdemServicoIdAndServicoId(long numeroOs, long idServico) {
        String sql = "SELECT * FROM itens_servico WHERE numero_os = ? AND id_servico = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, numeroOs);  // Número da Ordem de Serviço
            stmt.setLong(2, idServico); // ID do Serviço
    
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                // Buscando as entidades relacionadas (Serviço e Ordem de Serviço)
                Servico servico = servicoRepositoryImpl.buscarPorId(rs.getLong("id_servico"));
                OrdemServico ordemServico = ordemServicoRepositoryImpl.buscarPorId(rs.getLong("numero_os"));
    
                // Criando o objeto ItensServico a partir do resultado
                ItensServico itensServico = new ItensServico(
                    rs.getInt("id"),
                    rs.getTime("horario_inicio").toLocalTime(),
                    rs.getTime("horario_fim").toLocalTime(),
                    rs.getInt("quantidade"),
                    rs.getDouble("preco_total"),
                    funcionarioRepository.buscarPorId(rs.getLong("id_funcionario")),
                    servico,
                    ordemServico
                );
    
                return Optional.of(itensServico); // Retorna o item de serviço dentro de um Optional
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return Optional.empty(); // Caso não encontre o item, retorna Optional vazio
    }
    
    

}