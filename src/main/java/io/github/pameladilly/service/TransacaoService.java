package io.github.pameladilly.service;

import io.github.pameladilly.domain.entity.Transacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransacaoService {

    Transacao salvar(Transacao transacao);

    Transacao atualizar(Transacao transacao);

    void excluir(Transacao transacao);

    void excluir(Long id);

    Page<Transacao> pesquisar(Transacao filter, Pageable pageRequest);

    Transacao findById(Long id);
}
