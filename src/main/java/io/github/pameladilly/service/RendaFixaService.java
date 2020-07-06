package io.github.pameladilly.service;

import io.github.pameladilly.domain.entity.Carteira;
import io.github.pameladilly.domain.entity.RendaFixa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RendaFixaService {

    RendaFixa salvar(RendaFixa rendaFixa);

    RendaFixa atualizar(RendaFixa rendaFixa);

    void excluir(RendaFixa rendaFixa);

    Page<RendaFixa> pesquisar(RendaFixa filter, Pageable pageRequest);

    RendaFixa findById(Long id);

    //void excluir(Long id);
}
