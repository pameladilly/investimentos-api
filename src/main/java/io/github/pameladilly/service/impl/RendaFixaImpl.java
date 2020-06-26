package io.github.pameladilly.service.impl;

import io.github.pameladilly.domain.entity.RendaFixa;
import io.github.pameladilly.domain.repository.RendaFixaRepository;
import io.github.pameladilly.service.RendaFixaService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RendaFixaImpl implements RendaFixaService {

    final RendaFixaRepository repository;

    public RendaFixaImpl(RendaFixaRepository repository) {

        this.repository = repository;
    }

    @Override
    public RendaFixa salvar(RendaFixa rendaFixa) {

        rendaFixa.setDataCadastro( LocalDateTime.now());

        return repository.save(rendaFixa);

    }

    @Override
    public RendaFixa atualizar(RendaFixa rendaFixa) {

        return repository.save(rendaFixa);
    }

    @Override
    public void excluir(RendaFixa rendaFixa) {

        repository.delete(rendaFixa);
    }
}
