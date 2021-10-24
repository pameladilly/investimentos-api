package io.github.pameladilly.service.impl;

import io.github.pameladilly.domain.entity.RendaFixa;
import io.github.pameladilly.domain.repository.RendaFixaRepository;
import io.github.pameladilly.exception.RegraNegocioException;
import io.github.pameladilly.exception.rendafixa.RendaFixaNotFound;
import io.github.pameladilly.service.RendaFixaService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

         return repository.findById(rendaFixa.getIdAtivo()).map(
            entity -> {
                entity.setPreco(rendaFixa.getPreco());
                entity.setDescricao(rendaFixa.getDescricao());
                entity.setVencimento(rendaFixa.getVencimento());
                entity.setRentabilidadeDiaria(rendaFixa.getRentabilidadeDiaria());
                entity.setRentabilidadeMensal(rendaFixa.getRentabilidadeMensal());
                return repository.save(entity);
            }
        ).orElseThrow(RendaFixaNotFound::new);

    }

    @Override
    public void excluir(RendaFixa rendaFixa) {

       RendaFixa entity = repository.findById(rendaFixa.getIdAtivo()).orElseThrow(RendaFixaNotFound::new);

        repository.delete(entity);
    }

    @Override
    public Page<RendaFixa> pesquisar( RendaFixa filter, Pageable pageRequest) {

        Example<RendaFixa> example = Example.of(filter,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withIgnoreNullValues()
                        .withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING));

        return repository.findAll(example, pageRequest);


    }

    @Override
    public RendaFixa findById(Long id) {

        return repository.findById(id).orElseThrow(RendaFixaNotFound::new);

    }

    @Override
    public boolean existsById(Long id) {

        return repository.existsById(id);
    }
}
