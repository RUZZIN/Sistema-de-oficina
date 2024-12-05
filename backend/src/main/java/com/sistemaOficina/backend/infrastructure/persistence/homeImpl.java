package com.sistemaOficina.backend.infrastructure.persistence;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class homeImpl {

    private final Connection connection;

    public homeImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Método para obter o total de ordens de serviço
     */
    public int getTotalOrdensDeServico() {
        String query = "SELECT COUNT(*) AS total_os FROM ordem_servico";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_os");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Método para obter a receita total das ordens de serviço
     */
    public BigDecimal getReceitaTotal() {
        String query = "SELECT SUM(preco_final) AS receita_total FROM ordem_servico";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("receita_total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    /**
     * Método para obter o funcionário com mais serviços realizados
     */
    public String getFuncionarioComMaisServicos() {
        String query = """
            SELECT f.nome, COUNT(isv.id) AS total_servicos
            FROM itens_servico isv
            JOIN funcionario f ON isv.id_funcionario = f.id
            GROUP BY f.nome
            ORDER BY total_servicos DESC
            LIMIT 1
        """;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nome");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    /**
     * Método para obter a peça mais vendida
     */
    public String getPecaMaisVendida() {
        String query = """
            SELECT p.nome, SUM(ip.quantidade) AS total_vendida
            FROM itens_peca ip
            JOIN peca p ON ip.id_peca = p.id
            GROUP BY p.nome
            ORDER BY total_vendida DESC
            LIMIT 1
        """;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nome");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    /**
     * Método para obter o serviço mais solicitado
     */
    public String getServicoMaisSolicitado() {
        String query = """
            SELECT s.nome, SUM(isv.quantidade) AS total_solicitado
            FROM itens_servico isv
            JOIN servico s ON isv.id_servico = s.id
            GROUP BY s.nome
            ORDER BY total_solicitado DESC
            LIMIT 1
        """;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nome");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    /**
     * Método para obter o cliente com mais ordens de serviço
     */
    public String getClienteComMaisOrdensDeServico() {
        String query = """
            SELECT c.nome, COUNT(os.numero) AS total_os
            FROM ordem_servico os
            JOIN cliente c ON os.id_cliente = c.id
            GROUP BY c.nome
            ORDER BY total_os DESC
            LIMIT 1
        """;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nome");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    /**
     * Método para obter o veículo mais atendido
     */
    public String getVeiculoMaisAtendido() {
        String query = """
            SELECT v.placa, COUNT(os.numero) AS total_os
            FROM ordem_servico os
            JOIN veiculo v ON os.placa_veiculo = v.placa
            GROUP BY v.placa
            ORDER BY total_os DESC
            LIMIT 1
        """;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("placa");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "N/A";
    }
}
