package com.sistemaOficina.backend.repositorio;

import com.sistemaOficina.backend.entidade.Marca;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MarcaRepositoryImpl implements MarcaRepository {

    private final JdbcTemplate jdbcTemplate;

    // Construtor com a injeção do JdbcTemplate
    public MarcaRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void salvar(Marca marca) {
        String sql = "INSERT INTO marca (nome) VALUES (?)";
        jdbcTemplate.update(sql, marca.getNome());
    }

    @Override
    public void atualizar(Marca marca) {
        String sql = "UPDATE marca SET nome = ? WHERE id = ?";
        jdbcTemplate.update(sql, marca.getNome(), marca.getId());
    }

    @Override
    public void deletar(Long id) {
        String sql = "DELETE FROM marca WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Marca buscarPorId(Long id) {
        String sql = "SELECT * FROM marca WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Marca(
                rs.getLong("id"),
                rs.getString("nome")
        ), id);
    }

    @Override
    public List<Marca> buscarTodos() {
        String sql = "SELECT * FROM marca ORDER BY nome ASC"; // Ordenação alfabética
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Marca(
                rs.getLong("id"),
                rs.getString("nome")
        ));
    }
}
