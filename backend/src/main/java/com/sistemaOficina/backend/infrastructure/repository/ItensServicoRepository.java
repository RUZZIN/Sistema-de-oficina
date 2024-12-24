package com.sistemaOficina.backend.infrastructure.repository;
import java.util.List;

import com.sistemaOficina.backend.entity.*;

public interface ItensServicoRepository {
    void salvar(ItensServico itensServico);
    void atualizar(ItensServico itensServico);
    void deletar(int id);
    ItensServico buscarPorId(int id);
    List<ItensServico> buscarTodos();
}