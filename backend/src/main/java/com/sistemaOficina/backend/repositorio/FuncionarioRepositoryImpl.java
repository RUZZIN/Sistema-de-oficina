package com.sistemaOficina.backend.repositorio;

import com.sistemaOficina.backend.entidade.Funcionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        String sql = "INSERT INTO funcionarios (nome) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizar(Funcionario funcionario) {
        String sql = "UPDATE funcionarios SET nome = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setLong(2, funcionario.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(Long id) {
        String sql = "DELETE FROM funcionarios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Funcionario buscarPorId(Long id) {
        String sql = "SELECT * FROM funcionarios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Funcionario(rs.getLong("id"), rs.getString("nome"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Funcionario> buscarTodos() {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM funcionarios";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Funcionario(rs.getLong("id"), rs.getString("nome")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
