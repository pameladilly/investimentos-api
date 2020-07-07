package io.github.pameladilly.service.impl;

import io.github.pameladilly.domain.entity.RendaFixa;
import io.github.pameladilly.domain.entity.RendaVariavel;
import io.github.pameladilly.domain.repository.RendaVariavelRepository;
import io.github.pameladilly.exception.rendavariavel.RendaVariavelNotFound;
import io.github.pameladilly.service.RendaVariavelService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RendaVariavelImpl implements RendaVariavelService {


    final private RendaVariavelRepository repository;

    public RendaVariavelImpl(RendaVariavelRepository repository) {
        this.repository = repository;
    }



    @Override
    public RendaVariavel salvar(RendaVariavel rendaVariavel) {

        return repository.save(rendaVariavel);
    }

    @Override
    public RendaVariavel atualizar(RendaVariavel rendaVariavel) {

        return repository.findById(rendaVariavel.getIdAtivo()).map( entity -> {

            entity.setCotacao(rendaVariavel.getCotacao());
            entity.setTicker(rendaVariavel.getTicker());
            entity.setDescricao(rendaVariavel.getDescricao());
            return repository.save(entity);

        }).orElseThrow(RendaVariavelNotFound::new);

    }

    @Override
    public RendaVariavel atualizarCotacao(Long id, BigDecimal cotacao) {

        return  repository.findById(id).map( entity -> {
            entity.setCotacao(cotacao);
            return repository.save(entity);
        }).orElseThrow(RendaVariavelNotFound::new);
    }

    @Override
    public void excluir(RendaVariavel rendaVariavel) {

        repository.delete(rendaVariavel);
    }

    @Override
    public void excluir(Long id){

        excluir( repository.findById(id).orElseThrow(RendaVariavelNotFound::new));


    }

    @Override
    public Page<RendaVariavel> pesquisar(RendaVariavel filter, Pageable pageRequest) {

        Example<RendaVariavel> example = Example.of(filter,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withIgnoreNullValues()
                        .withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING));

        return repository.findAll(example, pageRequest);

    }

    @Override
    public RendaVariavel findById(Long id) {

        return repository.findById(id).orElseThrow(RendaVariavelNotFound::new);
    }
}
