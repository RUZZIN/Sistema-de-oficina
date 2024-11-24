package com.sistemaOficina.backend.repositorio;

import com.sistemaOficina.backend.entidade.ItensPeca;
import com.sistemaOficina.backend.entidade.OrdemServico;
import com.sistemaOficina.backend.entidade.Pecas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ItensPecaRepositoryImpl implements ItensPecaRepository {

    private final Connection connection;

    public ItensPecaRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void salvar(ItensPeca itensPeca) {
        String sql = "INSERT INTO itens_peca (preco_total, quantidade, numero_os, id_peca) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, itensPeca.getPrecoTotal());  // Preço total
            stmt.setInt(2, itensPeca.getQuantidade());     // Quantidade
            stmt.setLong(3, itensPeca.getNumeroOs().getNumero()); // Número da Ordem de Serviço
            stmt.setLong(4, itensPeca.getIdPeca().getId()); // ID da peça
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizar(ItensPeca itensPeca) {
        String sql = "UPDATE itens_peca SET preco_total = ?, quantidade = ?, numero_os = ?, id_peca = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, itensPeca.getPrecoTotal());  // Preço total
            stmt.setInt(2, itensPeca.getQuantidade());     // Quantidade
            stmt.setLong(3, itensPeca.getNumeroOs().getNumero()); // Número da Ordem de Serviço
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
                    new OrdemServico(rs.getInt("numero_os")),  // Construção da OrdemServico
                    new Pecas(rs.getLong("id_peca"))             // Construção da Pecas
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
                    new OrdemServico(rs.getInt("numero_os")),  // OrdemServico
                    new Pecas(rs.getLong("id_peca"))             // Pecas
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
