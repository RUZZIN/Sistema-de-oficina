package com.sistemaOficina.backend.repositorio;

import com.sistemaOficina.backend.entidade.Cliente;
import com.sistemaOficina.backend.entidade.PessoaFisica;
import com.sistemaOficina.backend.entidade.PessoaJuridica;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private final DataSource dataSource;

    public ClienteRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void salvar(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, logradouro, numero, complemento, ddi1, ddd1, numero1, ddi2, ddd2, numero2, email, cpf, cnpj, inscricao_estadual, contato, tipo_cliente) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            setCommonParameters(stmt, cliente);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nome = ?, logradouro = ?, numero = ?, complemento = ?, ddi1 = ?, ddd1 = ?, numero1 = ?, ddi2 = ?, ddd2 = ?, numero2 = ?, email = ?, cpf = ?, cnpj = ?, inscricao_estadual = ?, contato = ?, tipo_cliente = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            setCommonParameters(stmt, cliente);
            stmt.setLong(17, cliente.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(Long id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Cliente buscarPorId(Long id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToCliente(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Cliente> buscarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                clientes.add(mapResultSetToCliente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    private void setCommonParameters(PreparedStatement stmt, Cliente cliente) throws SQLException {
        stmt.setString(1, cliente.getNome());
        stmt.setString(2, cliente.getLogradouro());
        stmt.setString(3, cliente.getNumero());
        stmt.setString(4, cliente.getComplemento());
        stmt.setString(5, cliente.getDdi1());
        stmt.setString(6, cliente.getDdd1());
        stmt.setString(7, cliente.getNumero1());
        stmt.setString(8, cliente.getDdi2());
        stmt.setString(9, cliente.getDdd2());
        stmt.setString(10, cliente.getNumero2());
        stmt.setString(11, cliente.getEmail());
        stmt.setString(12, cliente instanceof PessoaFisica ? ((PessoaFisica) cliente).getCpf() : null);
        stmt.setString(13, cliente instanceof PessoaJuridica ? ((PessoaJuridica) cliente).getCnpj() : null);
        stmt.setString(14, cliente instanceof PessoaJuridica ? ((PessoaJuridica) cliente).getInscricaoEstadual() : null);
        stmt.setString(15, cliente.getContato());
        stmt.setString(16, cliente instanceof PessoaFisica ? "PessoaFisica" : "PessoaJuridica");
    }

    private Cliente mapResultSetToCliente(ResultSet rs) throws SQLException {
        String tipoCliente = rs.getString("tipo_cliente");
        if ("PessoaFisica".equals(tipoCliente)) {
            return new PessoaFisica(
                rs.getLong("id"),
                rs.getString("nome"),
                rs.getString("logradouro"),
                rs.getString("numero"),
                rs.getString("complemento"),
                rs.getString("ddi1"),
                rs.getString("ddd1"),
                rs.getString("numero1"),
                rs.getString("ddi2"),
                rs.getString("ddd2"),
                rs.getString("numero2"),
                rs.getString("email"),
                rs.getString("cpf")
            );
        } else if ("PessoaJuridica".equals(tipoCliente)) {
            return new PessoaJuridica(
                rs.getLong("id"),
                rs.getString("nome"),
                rs.getString("logradouro"),
                rs.getString("numero"),
                rs.getString("complemento"),
                rs.getString("ddi1"),
                rs.getString("ddd1"),
                rs.getString("numero1"),
                rs.getString("ddi2"),
                rs.getString("ddd2"),
                rs.getString("numero2"),
                rs.getString("email"),
                rs.getString("cnpj"),
                rs.getString("inscricao_estadual"),
                rs.getString("contato")
            );
        }
        return null;
    }
    
}