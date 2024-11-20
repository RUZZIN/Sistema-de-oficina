package com.sistemaOficina.backend.repositorio;

import com.sistemaOficina.backend.entidade.Modelo;
import com.sistemaOficina.backend.entidade.Veiculo;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VeiculoRepositoryImpl implements VeiculoRepository {

    private final DataSource dataSource;

    public VeiculoRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void salvar(Veiculo veiculo) {
        String sql = "INSERT INTO veiculos (placa, quilometragem, chassi, patrimonio, ano_modelo, ano_fabricacao, id_modelo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, veiculo.getPlaca());
            stmt.setInt(2, veiculo.getQuilometragem());
            stmt.setString(3, veiculo.getChassi());
            stmt.setString(4, veiculo.getPatrimonio());
            stmt.setInt(5, veiculo.getAnoModelo());
            stmt.setInt(6, veiculo.getAnoFabricacao());
            stmt.setLong(7, veiculo.getIdModelo().getId());  // ID do Modelo

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizar(Veiculo veiculo) {
        String sql = "UPDATE veiculos SET placa = ?, quilometragem = ?, chassi = ?, patrimonio = ?, ano_modelo = ?, ano_fabricacao = ?, id_modelo = ? WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, veiculo.getPlaca());
            stmt.setInt(2, veiculo.getQuilometragem());
            stmt.setString(3, veiculo.getChassi());
            stmt.setString(4, veiculo.getPatrimonio());
            stmt.setInt(5, veiculo.getAnoModelo());
            stmt.setInt(6, veiculo.getAnoFabricacao());
            stmt.setLong(7, veiculo.getIdModelo().getId());  // ID do Modelo
            stmt.setLong(8, veiculo.getId());  // ID do Veículo

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(Long id) {
        String sql = "DELETE FROM veiculos WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Veiculo buscarPorId(Long id) {
        String sql = "SELECT * FROM veiculos WHERE id = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToVeiculo(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Veiculo> buscarTodos() {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT * FROM veiculos";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                veiculos.add(mapResultSetToVeiculo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return veiculos;
    }

    private Veiculo mapResultSetToVeiculo(ResultSet rs) throws SQLException {
        // Aqui vamos mapear os campos para a entidade Veiculo
        Long idModelo = rs.getLong("id_modelo");
        Modelo modelo = new Modelo(idModelo);  // Criando objeto Modelo com ID

        return new Veiculo(
            rs.getLong("id"),
            rs.getString("placa"),
            rs.getInt("quilometragem"),
            rs.getString("chassi"),
            rs.getString("patrimonio"),
            rs.getInt("ano_modelo"),
            rs.getInt("ano_fabricacao"),
            modelo
        );
    }
}
