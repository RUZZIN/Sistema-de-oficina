package com.sistemaOficina.backend.infrastructure.persistence;

import com.sistemaOficina.backend.entity.*;
import com.sistemaOficina.backend.infrastructure.repository.ItensPecaRepository;
import com.sistemaOficina.backend.infrastructure.repository.PecasRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class ItensPecaRepositoryImpl implements ItensPecaRepository {

    private final Connection connection;
    private final PecasRepository pecasRepository;
    private final OrdemServicoRepositoryImpl ordemServicoRepositoryImpl;

    public ItensPecaRepositoryImpl(Connection connection, PecasRepository pecasRepository, OrdemServicoRepositoryImpl ordemServicoRepositoryImpl) {
        this.connection = connection;
        this.pecasRepository = pecasRepository;
        this.ordemServicoRepositoryImpl = ordemServicoRepositoryImpl;
    }

    @Override
public void salvar(ItensPeca itensPeca) {
    // O número da ordem de serviço é gerado na hora de salvar a ordem de serviço
    Long numeroOs = itensPeca.getNumeroOs().getNumero();

    String sql = "INSERT INTO itens_peca (preco_total, quantidade, numero_os, id_peca) VALUES (?, ?, ?, ?)";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setDouble(1, itensPeca.getPrecoTotal());
        stmt.setInt(2, itensPeca.getQuantidade());
        stmt.setLong(3, numeroOs); // Número da Ordem de Serviço
        stmt.setLong(4, itensPeca.getIdPeca().getId());
        stmt.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    @Override
    public void atualizar(ItensPeca itensPeca) {
        String sql = "UPDATE itens_peca SET preco_total = ?, quantidade = ?, numero_os = ?, id_peca = ? WHERE id = ?";

        System.err.println("bbbbbbbbbbbbb"+itensPeca);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, itensPeca.getPrecoTotal());  // Preço total
            stmt.setInt(2, itensPeca.getQuantidade());     // Quantidade
            stmt.setLong(3, itensPeca.getNumeroOs().getNumero());  // Atualizando com numero_os
            stmt.setLong(4, itensPeca.getIdPeca().getId()); // ID da peça
            stmt.setInt(5, itensPeca.getId());             // ID do Item de Peça
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM itens_peca WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id); // ID do Item de Peça
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ItensPeca buscarPorId(int id) {
        String sql = "SELECT * FROM itens_peca WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);  // ID do Item de Peça
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ItensPeca(
                    rs.getInt("id"),
                    rs.getDouble("preco_total"),
                    rs.getInt("quantidade"),
                    ordemServicoRepositoryImpl.buscarPorId(rs.getLong("numero_os")),  // Construção da OrdemServico
                    pecasRepository.buscarPorId(rs.getLong("id_peca"))             // Construção da Pecas
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ItensPeca> buscarTodos() {
        List<ItensPeca> lista = new ArrayList<>();
        String sql = "SELECT * FROM itens_peca";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new ItensPeca(
                    rs.getInt("id"),
                    rs.getDouble("preco_total"),
                    rs.getInt("quantidade"),
                    ordemServicoRepositoryImpl.buscarPorId(rs.getLong("numero_os")),  // Construção da OrdemServico
                    pecasRepository.buscarPorId(rs.getLong("id_peca"))              // Pecas
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    
    public List<ItensPeca> buscarPorNumeroOs(long numeroOs) {
        List<ItensPeca> lista = new ArrayList<>();
        String sql = "SELECT * FROM itens_peca WHERE numero_os = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, numeroOs);  // Set the parameter for the SQL query
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                // Fetch related entities for each row
                Pecas peca = pecasRepository.buscarPorId(rs.getLong("id_peca"));
                OrdemServico ordemServico = ordemServicoRepositoryImpl.buscarPorId(rs.getLong("numero_os"));
    
                // Create and add ItensPeca object to the list
                lista.add(new ItensPeca(
                    rs.getInt("id"),
                    rs.getDouble("preco_total"),
                    rs.getInt("quantidade"),
                    ordemServico,
                    peca
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return lista;
    }

    public Optional<ItensPeca> buscarPorOrdemServicoIdAndPecaId(long numeroOs, long idPeca) {
        String sql = "SELECT * FROM itens_peca WHERE numero_os = ? AND id_peca = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, numeroOs);  // Número da Ordem de Serviço
            stmt.setLong(2, idPeca);    // ID da Peça
    
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                Pecas peca = pecasRepository.buscarPorId(rs.getLong("id_peca"));
                OrdemServico ordemServico = ordemServicoRepositoryImpl.buscarPorId(rs.getLong("numero_os"));
    
                // Retorna o item de peça encontrado
                ItensPeca itensPeca = new ItensPeca(
                    rs.getInt("id"),
                    rs.getDouble("preco_total"),
                    rs.getInt("quantidade"),
                    ordemServico,
                    peca
                );
                return Optional.of(itensPeca); // Retorna o item de peça dentro de um Optional
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return Optional.empty();  // Retorna um Optional vazio caso não encontre o item
    }
    
    

}