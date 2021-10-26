package io.github.pameladilly.service.impl;

import io.github.pameladilly.domain.entity.Transacao;
import io.github.pameladilly.domain.repository.TransacaoRepository;
import io.github.pameladilly.exception.transacao.TransacaoNotFound;
import io.github.pameladilly.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransacaoServiceImpl implements TransacaoService {
    final private TransacaoRepository repository;

    @Override
    public Transacao salvar(Transacao transacao) {

        transacao.setTotal(transacao.getQuantidade().multiply(transacao.getValorUnitario()));

        return repository.save(transacao);
    }

    @Override
    public Transacao atualizar(Transacao transacao) {

        BigDecimal total = transacao.getQuantidade().multiply(transacao.getValorUnitario());

        return repository.findById(transacao.getIdTransacao()).map( entity -> {
                entity.setCarteira(transacao.getCarteira());
                entity.setQuantidade(transacao.getQuantidade());
                entity.setTipoTransacao(transacao.getTipoTransacao());
                entity.setValorUnitario(transacao.getValorUnitario());
                entity.setTotal( total );
                return repository.save(entity);

        }
        ).orElseThrow(TransacaoNotFound::new);
    }

    @Override
    public void excluir(Transacao transacao) {

        if (!repository.existsById(transacao.getIdTransacao())) {
            throw new TransacaoNotFound();
        }

        repository.delete(transacao);
    }

    @Override
    public void excluir(Long id) {

        Transacao transacao = repository.findById(id).orElseThrow(TransacaoNotFound::new);

        excluir(transacao);
    }

    @Override
    public Page<Transacao> pesquisar(Transacao filter, Pageable pageRequest) {
        Example<Transacao> example = Example.of(filter,
                ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return repository.findAll(example, pageRequest);

    }

    @Override
    public Transacao findById(Long id) {
        return repository.findById(id).orElseThrow(TransacaoNotFound::new);
    }
}
