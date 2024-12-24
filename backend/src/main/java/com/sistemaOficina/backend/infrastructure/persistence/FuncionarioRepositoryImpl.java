package com.sistemaOficina.backend.infrastructure.persistence;

import com.sistemaOficina.backend.entity.*;
import com.sistemaOficina.backend.infrastructure.repository.FuncionarioRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class FuncionarioRepositoryImpl implements FuncionarioRepository {

    private final Connection connection;

    public FuncionarioRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void salvar(Funcionario funcionario) {
        String sql = "INSERT INTO funcionario (nome, salario, data_nascimento, data_admissao, data_demissao, cargo, endereco, telefone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setBigDecimal(2, new BigDecimal(funcionario.getSalario()));  // Certifique-se que 'salario' é um BigDecimal ou double
            stmt.setDate(3, java.sql.Date.valueOf(funcionario.getDataNascimento())); // Converte LocalDate para SQL Date
            stmt.setDate(4, java.sql.Date.valueOf(funcionario.getDataAdmissao())); // Converte LocalDate para SQL Date
            stmt.setDate(5, funcionario.getDataDemissao() != null ? java.sql.Date.valueOf(funcionario.getDataDemissao())
                    : null);
            stmt.setString(6, funcionario.getCargo());
            stmt.setString(7, funcionario.getEndereco());
            stmt.setString(8, funcionario.getTelefone());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void atualizar(Funcionario funcionario) {
        String sql = "UPDATE funcionario SET nome = ?, salario = ?, data_nascimento = ?, data_admissao = ?, data_demissao = ?, cargo = ?, endereco = ?, telefone = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setBigDecimal(2, new BigDecimal(funcionario.getSalario())); // Cast correto
            stmt.setDate(3, java.sql.Date.valueOf(funcionario.getDataNascimento()));
            stmt.setDate(4, java.sql.Date.valueOf(funcionario.getDataAdmissao()));
            stmt.setDate(5, funcionario.getDataDemissao() != null ? java.sql.Date.valueOf(funcionario.getDataDemissao())
                    : null);
            stmt.setString(6, funcionario.getCargo());
            stmt.setString(7, funcionario.getEndereco());
            stmt.setString(8, funcionario.getTelefone());
            stmt.setLong(13, funcionario.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(Long id) {
        String sql = "DELETE FROM funcionario WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Funcionario buscarPorId(Long id) {
        String sql = "SELECT * FROM funcionario WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return criarFuncionario(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Funcionario> buscarTodos() {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM funcionario ORDER BY nome ASC"; // Ordenação alfabética
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(criarFuncionario(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private Funcionario criarFuncionario(ResultSet rs) throws SQLException {
        return new Funcionario(
                rs.getLong("id"),
                rs.getString("nome"),
                rs.getString("salario"),
                rs.getObject("data_nascimento", LocalDate.class),
                rs.getObject("data_admissao", LocalDate.class),
                rs.getObject("data_demissao", LocalDate.class),
                rs.getString("cargo"),
                rs.getString("endereco"),
                rs.getString("telefone"));
    }

    private void preencherStatement(Funcionario funcionario, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, funcionario.getNome());
        stmt.setString(2, funcionario.getSalario());
        stmt.setObject(3, funcionario.getDataNascimento());
        stmt.setObject(4, funcionario.getDataAdmissao());
        stmt.setObject(5, funcionario.getDataDemissao());
        stmt.setString(6, funcionario.getCargo());
        stmt.setString(7, funcionario.getEndereco());
        stmt.setString(8, funcionario.getTelefone());
    }
}
