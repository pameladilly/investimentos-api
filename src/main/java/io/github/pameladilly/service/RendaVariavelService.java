package io.github.pameladilly.service;

import io.github.pameladilly.domain.entity.RendaFixa;
import io.github.pameladilly.domain.entity.RendaVariavel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface RendaVariavelService {

    RendaVariavel salvar(RendaVariavel rendaVariavel);

    RendaVariavel atualizar(RendaVariavel rendaVariavel);

    RendaVariavel atualizarCotacao(Long id, BigDecimal cotacao);

    void excluir(RendaVariavel rendaVariavel);

     void excluir(Long id);

    Page<RendaVariavel> pesquisar(RendaVariavel filter, Pageable pageRequest);

    RendaVariavel findById(Long id);
}
