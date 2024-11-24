package com.sistemaOficina.backend.repositorio;

import com.sistemaOficina.backend.entidade.ItensServico;
import com.sistemaOficina.backend.entidade.Funcionario;
import com.sistemaOficina.backend.entidade.Servico;
import com.sistemaOficina.backend.entidade.OrdemServico; // Certifique-se de importar OrdemServico

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ItensServicoRepositoryImpl implements ItensServicoRepository {

    private final Connection connection;

    public ItensServicoRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void salvar(ItensServico itensServico) {
        String sql = "INSERT INTO itens_servico (horario_inicio, horario_fim, quantidade, preco_total, id_funcionario, id_servico, numero_os) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTime(1, Time.valueOf(itensServico.getHorarioInicio()));
            stmt.setTime(2, Time.valueOf(itensServico.getHorarioFim()));
            stmt.setInt(3, itensServico.getQuantidade());
            stmt.setDouble(4, itensServico.getPrecoTotal());
            stmt.setLong(5, itensServico.getIdFuncionario().getId());
            stmt.setLong(6, itensServico.getIdServico().getId());
            stmt.setLong(7, itensServico.getNumeroOs().getNumero());  // Supondo que 'numeroOs' seja um objeto de OrdemServico
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizar(ItensServico itensServico) {
        String sql = "UPDATE itens_servico SET horario_inicio = ?, horario_fim = ?, quantidade = ?, preco_total = ?, id_funcionario = ?, id_servico = ?, numero_os = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTime(1, Time.valueOf(itensServico.getHorarioInicio()));
            stmt.setTime(2, Time.valueOf(itensServico.getHorarioFim()));
            stmt.setInt(3, itensServico.getQuantidade());
            stmt.setDouble(4, itensServico.getPrecoTotal());
            stmt.setLong(5, itensServico.getIdFuncionario().getId());
            stmt.setLong(6, itensServico.getIdServico().getId());
            stmt.setLong(7, itensServico.getNumeroOs().getNumero());  // Atualizando com numero_os
            stmt.setInt(8, itensServico.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM itens_servico WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ItensServico buscarPorId(int id) {
        String sql = "SELECT * FROM itens_servico WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ItensServico(
                    rs.getInt("id"),
                    rs.getTime("horario_inicio").toLocalTime(),
                    rs.getTime("horario_fim").toLocalTime(),
                    rs.getInt("quantidade"),
                    rs.getDouble("preco_total"),
                    new Funcionario(rs.getLong("id_funcionario")), 
                    new Servico(null, null, rs.getInt("id_servico")),  
                    new OrdemServico(rs.getInt("numero_os"))  // Agora incluindo numero_os
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ItensServico> buscarTodos() {
        List<ItensServico> lista = new ArrayList<>();
        String sql = "SELECT * FROM itens_servico";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new ItensServico(
                    rs.getInt("id"),
                    rs.getTime("horario_inicio").toLocalTime(),
                    rs.getTime("horario_fim").toLocalTime(),
                    rs.getInt("quantidade"),
                    rs.getDouble("preco_total"),
                    new Funcionario(rs.getLong("id_funcionario")),
                    new Servico(null, null, rs.getInt("id_servico")),
                    new OrdemServico(rs.getInt("numero_os"))  // Incluindo numero_os na recuperação
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
