package com.sistemaOficina.backend.repositorio;

import com.sistemaOficina.backend.entidade.ItensServico;
import java.util.List;

public interface ItensServicoRepository {
    void salvar(ItensServico itensServico);
    void atualizar(ItensServico itensServico);
    void deletar(int id);
    ItensServico buscarPorId(int id);
    List<ItensServico> buscarTodos();
}