package com.sistemaOficina.backend.repositorio;

import com.sistemaOficina.backend.entidade.Servico;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ServicoRepositoryImpl implements ServicoRepository {

    private final List<Servico> bancoDeDadosSimulado = new ArrayList<>();

    @Override
    public void salvar(Servico servico) {
        servico.setId((long) (bancoDeDadosSimulado.size() + 1));
        bancoDeDadosSimulado.add(servico);
    }

    @Override
    public void atualizar(Servico servico) {
        deletar(servico.getId());
        bancoDeDadosSimulado.add(servico);
    }

    @Override
    public void deletar(Long id) {
        bancoDeDadosSimulado.removeIf(servico -> servico.getId().equals(id));
    }

    @Override
    public Servico buscarPorId(Long id) {
        Optional<Servico> servico = bancoDeDadosSimulado.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
        return servico.orElse(null);
    }

    @Override
    public List<Servico> buscarTodos() {
        return bancoDeDadosSimulado;
    }
}
