package com.sistemaOficina.backend.repositorio;

import com.sistemaOficina.backend.entidade.OrdemServico;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrdemServicoRepositoryImpl implements OrdemServicoRepository {

    private final DataSource dataSource;

    // Construtor que recebe o DataSource para a conexão com o banco
    public OrdemServicoRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Método auxiliar para obter a conexão com o banco
    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void salvar(OrdemServico ordemServico) {
        String sql = "INSERT INTO ordem_servico (data, preco_final, status, placa_veiculo) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection(); 
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(ordemServico.getData())); // Converte LocalDate para java.sql.Date
            statement.setDouble(2, ordemServico.getPrecoFinal());
            statement.setString(3, ordemServico.getStatus());
            statement.setString(4, ordemServico.getPlacaVeiculo());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar ordem de serviço", e);
        }
    }

    @Override
    public void atualizar(OrdemServico ordemServico) {
        String sql = "UPDATE ordem_servico SET data = ?, preco_final = ?, status = ?, placa_veiculo = ? WHERE numero = ?";

        try (Connection connection = getConnection(); 
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(ordemServico.getData()));
            statement.setDouble(2, ordemServico.getPrecoFinal());
            statement.setString(3, ordemServico.getStatus());
            statement.setString(4, ordemServico.getPlacaVeiculo());
            statement.setLong(5, ordemServico.getNumero());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar ordem de serviço", e);
        }
    }

    @Override
    public void deletar(Long id) {
        String sql = "DELETE FROM ordem_servico WHERE numero = ?";

        try (Connection connection = getConnection(); 
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao deletar ordem de serviço", e);
        }
    }

    @Override
    public OrdemServico buscarPorId(Long id) {
        String sql = "SELECT * FROM ordem_servico WHERE numero = ?";
        OrdemServico ordemServico = null;

        try (Connection connection = getConnection(); 
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    ordemServico = new OrdemServico(
                            resultSet.getLong("numero"),
                            resultSet.getDate("data").toLocalDate(),
                            resultSet.getDouble("preco_final"),
                            resultSet.getString("status"),
                            resultSet.getString("placa_veiculo")
                    );
                } else {
                    throw new SQLException("Ordem de serviço com ID " + id + " não encontrada.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar ordem de serviço com ID " + id, e);
        }

        return ordemServico;
    }

    @Override
    public List<OrdemServico> buscarTodos() {
        String sql = "SELECT * FROM ordem_servico";
        List<OrdemServico> listaOrdemDeServico = new ArrayList<>();

        try (Connection connection = getConnection(); 
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                OrdemServico ordemServico = new OrdemServico(
                        resultSet.getLong("numero"),
                        resultSet.getDate("data").toLocalDate(),
                        resultSet.getDouble("preco_final"),
                        resultSet.getString("status"),
                        resultSet.getString("placa_veiculo")
                );
                listaOrdemDeServico.add(ordemServico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar todas as ordens de serviço", e);
        }

        return listaOrdemDeServico;
    }
}
