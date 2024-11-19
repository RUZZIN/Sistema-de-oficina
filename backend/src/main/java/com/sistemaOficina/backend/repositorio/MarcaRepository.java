package com.sistemaOficina.backend.repositorio;
import java.util.List;

import com.sistemaOficina.backend.entidade.Marca;

public interface MarcaRepository {
    void salvar(Marca marca);
    void atualizar(Marca marca);
    void deletar(Long id);
    Marca buscarPorId(Long id);
    List<Marca> buscarTodos();
}
