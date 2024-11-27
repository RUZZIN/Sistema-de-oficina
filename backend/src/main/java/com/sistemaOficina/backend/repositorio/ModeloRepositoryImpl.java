package com.sistemaOficina.backend.repositorio;

import com.sistemaOficina.backend.entidade.Marca;
import com.sistemaOficina.backend.entidade.Modelo;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ModeloRepositoryImpl implements ModeloRepository {

    private final DataSource dataSource;
    private final MarcaRepositoryImpl marcaRepositoryImpl;

    public ModeloRepositoryImpl(DataSource dataSource, MarcaRepositoryImpl marcaRepositoryImpl) {
        this.dataSource = dataSource;
        this.marcaRepositoryImpl = marcaRepositoryImpl;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void salvar(Modelo modelo) {
        String sql = "INSERT INTO modelo (nome, id_marca) VALUES (?, ?)";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, modelo.getNome());
            stmt.setLong(2, modelo.getIdMarca().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizar(Modelo modelo) {
        String sql = "UPDATE modelo SET nome = ?, id_marca = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, modelo.getNome());
            stmt.setLong(2, modelo.getIdMarca().getId());
            stmt.setLong(3, modelo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(Long id) {
        String sql = "DELETE FROM modelo WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Modelo buscarPorId(Long id) {
        String sql = "SELECT * FROM modelo WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToModelo(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Modelo> buscarTodos() {
        List<Modelo> modelo = new ArrayList<>();
        String sql = "SELECT * FROM modelo";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                modelo.add(mapResultSetToModelo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelo;
    }

    private Modelo mapResultSetToModelo(ResultSet rs) throws SQLException {
        Marca marca = marcaRepositoryImpl.buscarPorId(rs.getLong("id_marca"));
        return new Modelo(
                rs.getLong("id"),
                rs.getString("nome"),
                marca
        );
    }
}
