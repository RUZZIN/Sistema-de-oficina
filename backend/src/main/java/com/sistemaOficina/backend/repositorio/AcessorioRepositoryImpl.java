package com.sistemaOficina.backend.repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sistemaOficina.backend.entidade.Acessorio;

public class AcessorioRepositoryImpl implements AcessorioRepository {

    private Connection connection;

    public AcessorioRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void salvar(Acessorio acessorio) {
        String sql = "INSERT INTO acessorios (descricao) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, acessorio.getNome());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizar(Acessorio acessorio) {
        String sql = "UPDATE acessorios SET descricao = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, acessorio.getNome());
            stmt.setLong(2, acessorio.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(Long id) {
        String sql = "DELETE FROM acessorios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Acessorio buscarPorId(Long id) {
        String sql = "SELECT * FROM acessorios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Acessorio(rs.getLong("id"), rs.getString("nome"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Acessorio> buscarTodos() {
        List<Acessorio> lista = new ArrayList<>();
        String sql = "SELECT * FROM acessorios";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Acessorio(rs.getLong("id"), rs.getString("nome")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
