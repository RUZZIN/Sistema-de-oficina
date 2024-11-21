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
        String sql = "INSERT INTO cliente (nome, logradouro, numero, complemento, ddi1, ddd1, numero1, ddi2, ddd2, numero2, email, cpf, cnpj, inscricao_estadual, contato, tipo_cliente) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            setCommonParameters(stmt, cliente);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET nome = ?, logradouro = ?, numero = ?, complemento = ?, ddi1 = ?, ddd1 = ?, numero1 = ?, ddi2 = ?, ddd2 = ?, numero2 = ?, email = ?, cpf = ?, cnpj = ?, inscricao_estadual = ?, contato = ?, tipo_cliente = ? WHERE id = ?";
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
        String sql = "DELETE FROM cliente WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Cliente buscarPorId(Long id) {
        String sql = "SELECT * FROM cliente WHERE id = ?";
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
        List<Cliente> cliente = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cliente.add(mapResultSetToCliente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }

    private void setCommonParameters(PreparedStatement stmt, Cliente cliente) throws SQLException {
        stmt.setString(1, cliente.getNome());
        stmt.setString(2, cliente.getLogradouro());
        stmt.setString(3, cliente.getNumero());
        stmt.setString(4, cliente.getComplemento());
        stmt.setInt(5, cliente.getDdi1()); // Agora Integer
        stmt.setInt(6, cliente.getDdd1()); // Agora Integer
        stmt.setInt(7, cliente.getNumero1()); // Agora Integer
    
        // Valores que podem ser nulos
        if (cliente.getDdi2() != null) {
            stmt.setInt(8, cliente.getDdi2());
        } else {
            stmt.setNull(8, java.sql.Types.INTEGER);
        }
    
        if (cliente.getDdd2() != null) {
            stmt.setInt(9, cliente.getDdd2());
        } else {
            stmt.setNull(9, java.sql.Types.INTEGER);
        }
    
        if (cliente.getNumero2() != null) {
            stmt.setInt(10, cliente.getNumero2());
        } else {
            stmt.setNull(10, java.sql.Types.INTEGER);
        }
    
        stmt.setString(11, cliente.getEmail());
        stmt.setString(12, cliente.getCpf());
        stmt.setString(13, cliente.getCnpj());
        stmt.setString(14, cliente.getInscricaoEstadual());
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
                rs.getInt("ddi1"),
                rs.getInt("ddd1"),
                rs.getInt("numero1"),
                rs.getObject("ddi2", Integer.class),
                rs.getObject("ddd2", Integer.class),
                rs.getObject("numero2", Integer.class),
                rs.getString("email"),
                rs.getString("cpf"), tipoCliente, tipoCliente, tipoCliente, tipoCliente
            );
        } else if ("PessoaJuridica".equals(tipoCliente)) {
            return new PessoaJuridica(
                rs.getLong("id"),
                rs.getString("nome"),
                rs.getString("logradouro"),
                rs.getString("numero"),
                rs.getString("complemento"),
                rs.getInt("ddi1"),
                rs.getInt("ddd1"),
                rs.getInt("numero1"),
                rs.getObject("ddi2", Integer.class),
                rs.getObject("ddd2", Integer.class),
                rs.getObject("numero2", Integer.class),
                rs.getString("email"),
                rs.getString("cnpj"),
                rs.getString("inscricao_estadual"),
                rs.getString("contato"), tipoCliente, tipoCliente, tipoCliente, tipoCliente, tipoCliente
            );
        }
        return null;
    }
    
}