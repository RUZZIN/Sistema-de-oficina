package com.sistemaOficina.backend.repositorio;

import com.sistemaOficina.backend.entidade.Pecas;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PecasRepositoryImpl implements PecasRepository {

    private final DataSource dataSource;

    public PecasRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }

    @Override
    public void salvar(Pecas pecas) {
        String sql = "INSERT INTO pecas (codigo, nome, preco_unitario, quantidade) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pecas.getCodigo());
            stmt.setString(2, pecas.getNome());
            stmt.setDouble(3, pecas.getPrecoUnitario());
            stmt.setInt(4, pecas.getQuantidade());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizar(Pecas pecas) {
        String sql = "UPDATE pecas SET codigo = ?, nome = ?, preco_unitario = ?, quantidade = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pecas.getCodigo());
            stmt.setString(2, pecas.getNome());
            stmt.setDouble(3, pecas.getPrecoUnitario());
            stmt.setInt(4, pecas.getQuantidade());
            stmt.setInt(5, pecas.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(Long id) {
        String sql = "DELETE FROM pecas WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Pecas buscarPorId(Long id) {
        String sql = "SELECT * FROM pecas WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Pecas(
                        rs.getInt("id"),
                        rs.getString("codigo"),
                        rs.getString("nome"),
                        rs.getDouble("preco_unitario"),
                        rs.getInt("quantidade")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Pecas> buscarTodos() {
        List<Pecas> lista = new ArrayList<>();
        String sql = "SELECT * FROM pecas";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Pecas(
                        rs.getInt("id"),
                        rs.getString("codigo"),
                        rs.getString("nome"),
                        rs.getDouble("preco_unitario"),
                        rs.getInt("quantidade")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
