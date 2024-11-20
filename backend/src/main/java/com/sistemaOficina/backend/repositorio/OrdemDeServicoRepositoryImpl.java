package com.sistemaOficina.backend.repositorio;

import com.sistemaOficina.backend.entidade.OrdemDeServico;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrdemDeServicoRepositoryImpl implements OrdemDeServicoRepository {

    private final DataSource dataSource;

    // Construtor que recebe o DataSource para a conexão com o banco
    public OrdemDeServicoRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Método auxiliar para obter a conexão com o banco
    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void salvar(OrdemDeServico ordemDeServico) {
        String sql = "INSERT INTO ordem_de_servico (numero, preco_final, data, status, id_itens_peca, id_itens_servico, id_veiculo) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ordemDeServico.getNumero());
            statement.setString(2, ordemDeServico.getPrecoFinal());
            statement.setTimestamp(3, Timestamp.valueOf(ordemDeServico.getData()));
            statement.setString(4, ordemDeServico.getStatus());
            statement.setInt(5, ordemDeServico.getIdItensPeca().getId());  // Presumindo que IdItensPeca tem o método getId()
            statement.setInt(6, ordemDeServico.getIdItensServico().getId()); // Presumindo que IdItensServico tem o método getId()
            statement.setLong(7, ordemDeServico.getIdVeiculo().getId());     // Presumindo que Veiculo tem o método getId()
            
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizar(OrdemDeServico ordemDeServico) {
        String sql = "UPDATE ordem_de_servico SET preco_final = ?, data = ?, status = ?, id_itens_peca = ?, " +
                     "id_itens_servico = ?, id_veiculo = ? WHERE numero = ?";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, ordemDeServico.getPrecoFinal());
            statement.setTimestamp(2, Timestamp.valueOf(ordemDeServico.getData()));
            statement.setString(3, ordemDeServico.getStatus());
            statement.setInt(4, ordemDeServico.getIdItensPeca().getId());
            statement.setInt(5, ordemDeServico.getIdItensServico().getId());
            statement.setLong(6, ordemDeServico.getIdVeiculo().getId());
            statement.setInt(7, ordemDeServico.getNumero());
            
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(Long id) {
        String sql = "DELETE FROM ordem_de_servico WHERE numero = ?";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public OrdemDeServico buscarPorId(Long id) {
        String sql = "SELECT * FROM ordem_de_servico WHERE numero = ?";
        OrdemDeServico ordemDeServico = null;

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                ordemDeServico = new OrdemDeServico(
                    resultSet.getInt("numero"),
                    resultSet.getString("preco_final"),
                    resultSet.getTimestamp("data").toLocalDateTime(),
                    resultSet.getString("status"),
                    // Adicionar os objetos de ItensPeca, ItensServico, Veiculo aqui
                    null, // Placeholder, pois depende de como você implementou as entidades
                    null,
                    null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ordemDeServico;
    }

    @Override
    public List<OrdemDeServico> buscarTodos() {
        String sql = "SELECT * FROM ordem_de_servico";
        List<OrdemDeServico> ordensDeServico = new ArrayList<>();

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                OrdemDeServico ordemDeServico = new OrdemDeServico(
                    resultSet.getInt("numero"),
                    resultSet.getString("preco_final"),
                    resultSet.getTimestamp("data").toLocalDateTime(),
                    resultSet.getString("status"),
                    // Adicionar os objetos de ItensPeca, ItensServico, Veiculo aqui
                    null, // Placeholder, pois depende de como você implementou as entidades
                    null,
                    null
                );
                ordensDeServico.add(ordemDeServico);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ordensDeServico;
    }
}
