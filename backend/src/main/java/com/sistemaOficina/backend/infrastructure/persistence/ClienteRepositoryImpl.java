package com.sistemaOficina.backend.infrastructure.persistence;

import org.springframework.stereotype.Repository;

import com.sistemaOficina.backend.entity.*;
import com.sistemaOficina.backend.exception.ClienteNaoEncontradoException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteRepositoryImpl {
    private final DataSource dataSource;

    public ClienteRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Obter conexão com o banco de dados
    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // Salvar ou atualizar cliente
    public void salvar(Cliente cliente) {
        String sql;

        // Se o cliente não tem ID (é um novo cliente), faz um INSERT
        if (cliente.getId() == null) {
            sql = "INSERT INTO cliente (nome, logradouro, numero, complemento, ddi1, ddd1, numero1, ddi2, ddd2, numero2, email, cpf, cnpj, inscricao_estadual, contato, tipo_cliente) "
                    +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            // Caso contrário, faz um UPDATE
            sql = "UPDATE cliente SET nome = ?, logradouro = ?, numero = ?, complemento = ?, ddi1 = ?, ddd1 = ?, numero1 = ?, ddi2 = ?, ddd2 = ?, numero2 = ?, email = ?, "
                    +
                    "cpf = ?, cnpj = ?, inscricao_estadual = ?, contato = ?, tipo_cliente = ? WHERE id = ?";
        }

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, cliente.getNome());
            preparedStatement.setString(2, cliente.getLogradouro());
            preparedStatement.setString(3, cliente.getNumero());
            preparedStatement.setString(4, cliente.getComplemento());
            preparedStatement.setInt(5, cliente.getDdi1());
            preparedStatement.setInt(6, cliente.getDdd1());
            preparedStatement.setInt(7, cliente.getNumero1());
            preparedStatement.setObject(8, cliente.getDdi2(), Types.INTEGER);
            preparedStatement.setObject(9, cliente.getDdd2(), Types.INTEGER);
            preparedStatement.setObject(10, cliente.getNumero2(), Types.INTEGER);
            preparedStatement.setString(11, cliente.getEmail());
            preparedStatement.setString(12, cliente.getCpf());
            preparedStatement.setString(13, cliente.getCnpj());
            preparedStatement.setString(14, cliente.getInscricaoEstadual());
            preparedStatement.setString(15, cliente.getContato());
            preparedStatement.setString(16, cliente.getTipoCliente());

            // Se for uma atualização, adiciona o ID ao PreparedStatement
            if (cliente.getId() != null) {
                preparedStatement.setLong(17, cliente.getId());
            }

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar ou atualizar o cliente", e);
        }
    }

    // Buscar todos os clientes
    public List<Cliente> buscarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente ORDER BY nome ASC"; // Ordenação alfabética

        try (Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getLong("id"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setLogradouro(resultSet.getString("logradouro"));
                cliente.setNumero(resultSet.getString("numero"));
                cliente.setComplemento(resultSet.getString("complemento"));
                cliente.setDdi1(resultSet.getInt("ddi1"));
                cliente.setDdd1(resultSet.getInt("ddd1"));
                cliente.setNumero1(resultSet.getInt("numero1"));
                cliente.setDdi2((Integer) resultSet.getObject("ddi2"));
                cliente.setDdd2((Integer) resultSet.getObject("ddd2"));
                cliente.setNumero2((Integer) resultSet.getObject("numero2"));
                cliente.setEmail(resultSet.getString("email"));
                cliente.setCpf(resultSet.getString("cpf"));
                cliente.setCnpj(resultSet.getString("cnpj"));
                cliente.setInscricaoEstadual(resultSet.getString("inscricao_estadual"));
                cliente.setContato(resultSet.getString("contato"));
                cliente.setTipoCliente(resultSet.getString("tipo_cliente"));
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar os clientes", e);
        }

        return clientes;
    }

    public Cliente buscarPorId(Long id) {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId(resultSet.getLong("id"));
                    cliente.setNome(resultSet.getString("nome"));
                    cliente.setLogradouro(resultSet.getString("logradouro"));
                    cliente.setNumero(resultSet.getString("numero"));
                    cliente.setComplemento(resultSet.getString("complemento"));
                    cliente.setDdi1(resultSet.getInt("ddi1"));
                    cliente.setDdd1(resultSet.getInt("ddd1"));
                    cliente.setNumero1(resultSet.getInt("numero1"));
                    cliente.setDdi2((Integer) resultSet.getObject("ddi2"));
                    cliente.setDdd2((Integer) resultSet.getObject("ddd2"));
                    cliente.setNumero2((Integer) resultSet.getObject("numero2"));
                    cliente.setEmail(resultSet.getString("email"));
                    cliente.setCpf(resultSet.getString("cpf"));
                    cliente.setCnpj(resultSet.getString("cnpj"));
                    cliente.setInscricaoEstadual(resultSet.getString("inscricao_estadual"));
                    cliente.setContato(resultSet.getString("contato"));
                    cliente.setTipoCliente(resultSet.getString("tipo_cliente"));
                    return cliente;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar o cliente por ID", e);
        }

        // Lança exceção caso o cliente não seja encontrado
        throw new ClienteNaoEncontradoException("Cliente com ID " + id + " não encontrado.");
    }

    // Deletar cliente por ID
    public void deletar(Long id) {
        String sql = "DELETE FROM cliente WHERE id = ?";

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao deletar o cliente", e);
        }
    }
}