package com.sistemaOficina.backend.infrastructure.persistence;

import com.sistemaOficina.backend.entity.*;
import com.sistemaOficina.backend.infrastructure.repository.ServicoRepository;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ServicoRepositoryImpl implements ServicoRepository {

    private final DataSource dataSource;

    // Construtor que recebe o DataSource para a conexão com o banco
    public ServicoRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Método auxiliar para obter a conexão com o banco
    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void salvar(Servico servico) {
        String sql = "INSERT INTO servico (nome, preco_unitario) VALUES (?, ?)";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, servico.getNome());
            statement.setDouble(2, servico.getPrecoUnitario());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar serviço", e);
        }
    }

    @Override
    public void atualizar(Servico servico) {
        String sql = "UPDATE servico SET nome = ?, preco_unitario = ? WHERE id = ?";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, servico.getNome());
            statement.setDouble(2, servico.getPrecoUnitario());
            statement.setLong(3, servico.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar serviço", e);
        }
    }

    @Override
    public void deletar(Long id) {
        String sql = "DELETE FROM servico WHERE id = ?";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao deletar serviço", e);
        }
    }

    @Override
    public Servico buscarPorId(Long id) {
        String sql = "SELECT * FROM servico WHERE id = ?";
        Servico servico = null;

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    servico = new Servico(
                            resultSet.getLong("id"),
                            resultSet.getString("nome"),
                            resultSet.getDouble("preco_unitario")
                    );
                } else {
                    throw new SQLException("Serviço com ID " + id + " não encontrado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar serviço com ID " + id, e);
        }

        return servico;
    }

    @Override
    public List<Servico> buscarTodos() {
        String sql = "SELECT * FROM servico ORDER BY nome ASC"; // Ordena os serviços pelo nome em ordem alfabética crescente
        List<Servico> servicos = new ArrayList<>();

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Servico servico = new Servico(
                        resultSet.getLong("id"),
                        resultSet.getString("nome"),
                        resultSet.getDouble("preco_unitario")
                );
                servicos.add(servico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar todos os serviços", e);
        }

        return servicos;
    }
}
