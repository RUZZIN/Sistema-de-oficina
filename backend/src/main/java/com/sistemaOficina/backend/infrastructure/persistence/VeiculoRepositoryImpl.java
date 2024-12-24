package com.sistemaOficina.backend.infrastructure.persistence;

import com.sistemaOficina.backend.entity.*;
import com.sistemaOficina.backend.infrastructure.repository.VeiculoRepository;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VeiculoRepositoryImpl implements VeiculoRepository {

    private final DataSource dataSource;

    private final ModeloRepositoryImpl modeloRepositoryImpl;

    public VeiculoRepositoryImpl(DataSource dataSource, ModeloRepositoryImpl modeloRepositoryImpl) {
        this.dataSource = dataSource;
        this.modeloRepositoryImpl = modeloRepositoryImpl;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void salvar(Veiculo veiculo) {
        String sql = "INSERT INTO veiculo (placa, quilometragem, chassi, patrimonio, ano_modelo, ano_fabricacao, id_modelo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
        	System.out.println("Veiculo: "+veiculo.toString());
            stmt.setString(1, veiculo.getPlaca());
            stmt.setInt(2, veiculo.getQuilometragem());
            stmt.setString(3, veiculo.getChassi());
            stmt.setString(4, veiculo.getPatrimonio());
            stmt.setInt(5, veiculo.getAnoModelo());
            stmt.setInt(6, veiculo.getAnoFabricacao());
            System.out.println("IDmodelo: "+veiculo.getIdModelo());
            stmt.setLong(7, veiculo.getIdModelo().getId()); 
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizar(String placaAntiga, Veiculo veiculo) {
    	System.out.println("Placa antiga: "+placaAntiga);
        String sql = "UPDATE veiculo SET placa = ?, quilometragem = ?, chassi = ?, patrimonio = ?, ano_modelo = ?, ano_fabricacao = ?, id_modelo = ? WHERE placa = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
        	System.out.println("Veiculo atualização: "+veiculo.toString());
            stmt.setString(1, veiculo.getPlaca());
            stmt.setInt(2, veiculo.getQuilometragem());
            stmt.setString(3, veiculo.getChassi());
            stmt.setString(4, veiculo.getPatrimonio());
            stmt.setInt(5, veiculo.getAnoModelo());
            stmt.setInt(6, veiculo.getAnoFabricacao());
            stmt.setLong(7, veiculo.getIdModelo().getId());  // ID do Modelo
            stmt.setString(8, placaAntiga); // Placa original para a cláusula WHERE

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(String placa) {
        String sql = "DELETE FROM veiculo WHERE placa = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, placa);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Veiculo buscarPorPlaca(String placa) {
        String sql = "SELECT * FROM veiculo WHERE placa = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, placa);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToVeiculo(rs);
            } else {
                // Adicione um log para verificar a situação
                System.out.println("Veículo com placa " + placa + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    

    @Override
    public List<Veiculo> buscarTodos() {
        List<Veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT * FROM veiculo ORDER BY placa ASC"; // Ordena pela placa em ordem alfabética crescente
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
        // Mapeia os resultados para a entidade Veiculo
        Long idModelo = rs.getLong("id_modelo");
        Modelo modelo = modeloRepositoryImpl.buscarPorId(idModelo);
        return new Veiculo(
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
