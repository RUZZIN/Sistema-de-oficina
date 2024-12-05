package com.sistemaOficina.backend.infrastructure.repository;
import com.sistemaOficina.backend.core.entidade.*;

import java.util.List;

public interface ItensServicoRepository {
    void salvar(ItensServico itensServico);
    void atualizar(ItensServico itensServico);
    void deletar(int id);
    ItensServico buscarPorId(int id);
    List<ItensServico> buscarTodos();
}