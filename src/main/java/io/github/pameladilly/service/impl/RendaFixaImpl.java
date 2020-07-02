package io.github.pameladilly.service.impl;

import io.github.pameladilly.domain.entity.RendaFixa;
import io.github.pameladilly.domain.repository.RendaFixaRepository;
import io.github.pameladilly.exception.RegraNegocioException;
import io.github.pameladilly.exception.rendafixa.RendaFixaNotFound;
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

        if (rendaFixa.getUsuario() == null) {
            throw new RegraNegocioException("Usuário não informado.");
        }


        return repository.save(rendaFixa);

    }

    @Override
    public RendaFixa atualizar(RendaFixa rendaFixa) {

        return repository.save(rendaFixa);
    }

    @Override
    public void excluir(RendaFixa rendaFixa) {

       RendaFixa entity = repository.findById(rendaFixa.getIdAtivo()).orElseThrow(RendaFixaNotFound::new);

        repository.delete(entity);
    }
}
