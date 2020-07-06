package io.github.pameladilly.service;

import io.github.pameladilly.domain.entity.RendaFixa;
import io.github.pameladilly.domain.entity.RendaVariavel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RendaVariavelService {

    RendaVariavel salvar(RendaVariavel rendaVariavel);

    RendaVariavel atualizar(RendaVariavel rendaVariavel);

    void excluir(RendaVariavel rendaVariavel);

    Page<RendaVariavel> pesquisar(RendaVariavel filter, Pageable pageRequest);

    RendaVariavel findById(Long id);
}
