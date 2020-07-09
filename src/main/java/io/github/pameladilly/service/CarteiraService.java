package io.github.pameladilly.service;

import io.github.pameladilly.domain.entity.Carteira;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.concurrent.CancellationException;

public interface CarteiraService {

    Carteira salvar(Carteira carteira);

    Carteira atualizar(Long id, Carteira carteira);

    Boolean excluir(Carteira carteira);

    void excluir(Long id);

    Page<Carteira> pesquisar(Carteira filter, Pageable pageRequest);

    Boolean existsById(Long id);

    Carteira findById(Long id);

}
