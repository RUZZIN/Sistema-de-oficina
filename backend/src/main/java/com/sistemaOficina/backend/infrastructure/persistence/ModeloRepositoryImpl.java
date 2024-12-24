package com.sistemaOficina.backend.infrastructure.persistence;

import com.sistemaOficina.backend.entity.*;
import com.sistemaOficina.backend.infrastructure.repository.ModeloRepository;
import com.sistemaOficina.backend.infrastructure.service.*;

import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ModeloRepositoryImpl implements ModeloRepository {

    private final DataSource dataSource;
    private final MarcaService marcaService;

    public ModeloRepositoryImpl(DataSource dataSource, MarcaService marcaService) {
        this.dataSource = dataSource;
        this.marcaService = marcaService;
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
        String sql = "SELECT * FROM modelo ORDER BY nome ASC"; // Ordenação alfabética
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
        Marca marca = marcaService.buscarPorId(rs.getLong("id_marca")).orElse(null);
        return new Modelo(
                rs.getLong("id"),
                rs.getString("nome"),
                marca
        );
    }
}
