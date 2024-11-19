package com.sistemaOficina.backend.repositorio;

import com.sistemaOficina.backend.entidade.Marca;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MarcaRepositoryImpl implements MarcaRepository {

    private final List<Marca> bancoDeDadosSimulado = new ArrayList<>();

    @Override
    public void salvar(Marca marca) {
        marca.setId((long) (bancoDeDadosSimulado.size() + 1));
        bancoDeDadosSimulado.add(marca);
    }

    @Override
    public void atualizar(Marca marca) {
        deletar(marca.getId());
        bancoDeDadosSimulado.add(marca);
    }

    @Override
    public void deletar(Long id) {
        bancoDeDadosSimulado.removeIf(marca -> marca.getId().equals(id));
    }

    @Override
    public Marca buscarPorId(Long id) {
        Optional<Marca> marca = bancoDeDadosSimulado.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
        return marca.orElse(null);
    }

    @Override
    public List<Marca> buscarTodos() {
        return bancoDeDadosSimulado;
    }
}
