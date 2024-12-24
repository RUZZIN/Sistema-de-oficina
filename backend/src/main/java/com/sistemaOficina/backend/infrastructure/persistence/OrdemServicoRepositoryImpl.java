package com.sistemaOficina.backend.infrastructure.persistence;

import com.sistemaOficina.backend.infrastructure.repository.OrdemServicoRepository;

import org.springframework.stereotype.Repository;

import com.sistemaOficina.backend.entity.*;
import com.sistemaOficina.backend.infrastructure.repository.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrdemServicoRepositoryImpl implements OrdemServicoRepository {

    private final DataSource dataSource;
    private final VeiculoRepository veiculoRepository;
    private final ClienteRepositoryImpl clienteRepository;

    // Construtor que recebe o DataSource para a conexão com o banco
    public OrdemServicoRepositoryImpl(DataSource dataSource, VeiculoRepository veiculoRepository, ClienteRepositoryImpl clienteRepository) {
        this.dataSource = dataSource;
        this.veiculoRepository = veiculoRepository;
        this.clienteRepository = clienteRepository;
    }

    // Método auxiliar para obter a conexão com o banco
    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public Long buscarUltimoNumeroOs() {
        String sql = "SELECT MAX(numero) FROM ordem_servico";
        
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
        
            if (resultSet.next()) {
                return resultSet.getLong(1);  // Retorna o maior número da coluna numero_os
            } else {
                return null;  // Se não houver nenhum número (ou tabela vazia)
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar o último número de ordem de serviço", e);
        }
    }
    
    
    @Override
    public void salvar(OrdemServico ordemServico) {
        // Obtenha o próximo número da ordem de serviço

        // Incluindo o número da ordem de serviço no SQL
        String sql = "INSERT INTO ordem_servico (numero, data, preco_final, status, placa_veiculo, id_cliente) VALUES (?, ?, ?, ?, ?, ?)";
    
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
    
            statement.setLong(1, ordemServico.getNumero());
            statement.setDate(2, Date.valueOf(ordemServico.getData()));
            statement.setDouble(3, ordemServico.getPrecoFinal());
            statement.setString(4, ordemServico.getStatus());
            statement.setString(5, ordemServico.getPlacaVeiculo().getPlaca());
            statement.setLong(6, ordemServico.getCliente().getId());
    
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar ordem de serviço", e);
        }
    }
    
    

    @Override
public void atualizar(OrdemServico ordemServico) {
    String sql = "UPDATE ordem_servico SET data = ?, preco_final = ?, status = ?, placa_veiculo = ?, id_cliente = ? WHERE numero = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        // Ajuste no índice de cada parâmetro conforme a ordem da SQL
        statement.setDate(1, Date.valueOf(ordemServico.getData()));        // data
        statement.setDouble(2, ordemServico.getPrecoFinal());              // preco_final
        statement.setString(3, ordemServico.getStatus());                 // status
        statement.setString(4, ordemServico.getPlacaVeiculo().getPlaca()); // placa_veiculo
        statement.setLong(5, ordemServico.getCliente().getId());           
        statement.setLong(6, ordemServico.getNumero());                    // numero (WHERE)

        statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Erro ao atualizar ordem de serviço", e);
    }
}


    public int buscarQuantidadePorPecaEOrdemServico(Long numeroOrdemServico, Long idPeca) {
        String sql = "SELECT quantidade FROM itens_peca WHERE numero_os = ? AND id_peca = ?";
        
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setLong(1, numeroOrdemServico);
            statement.setLong(2, idPeca);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("quantidade");
                } else {
                    return 0; // Retorna 0 se não houver registro para essa peça na OS
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar quantidade da peça na ordem de serviço", e);
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
                    Veiculo veiculo = veiculoRepository.buscarPorPlaca(resultSet.getString("placa_veiculo"));
                    Cliente cliente = clienteRepository.buscarPorId(resultSet.getLong("id_cliente"));
                    System.err.println("Cliente23: " + cliente);
                    //pega do impl veiculo o buscar por placa

                    ordemServico = new OrdemServico(
                            resultSet.getLong("numero"),
                            resultSet.getDate("data").toLocalDate(),
                            resultSet.getDouble("preco_final"),
                            resultSet.getString("status"),
                            veiculo,
                            cliente
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
        String sql = "SELECT * FROM ordem_servico ORDER BY numero ASC";
        List<OrdemServico> listaOrdemDeServico = new ArrayList<>();

        try (Connection connection = getConnection(); 
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Veiculo veiculo = veiculoRepository.buscarPorPlaca(resultSet.getString("placa_veiculo"));

                OrdemServico ordemServico = new OrdemServico(
                        resultSet.getLong("numero"),
                        resultSet.getDate("data").toLocalDate(),
                        resultSet.getDouble("preco_final"),
                        resultSet.getString("status"),
                        veiculo,
                        clienteRepository.buscarPorId(resultSet.getLong("id_cliente"))
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
