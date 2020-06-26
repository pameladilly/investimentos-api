package io.github.pameladilly.service;

import io.github.pameladilly.domain.entity.RendaFixa;

public interface RendaFixaService {

    RendaFixa salvar(RendaFixa rendaFixa);

    RendaFixa atualizar(RendaFixa rendaFixa);

    void excluir(RendaFixa rendaFixa);


    //void excluir(Long id);
}
