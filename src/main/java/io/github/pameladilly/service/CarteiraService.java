package io.github.pameladilly.service;

import io.github.pameladilly.domain.entity.Carteira;

import java.util.Optional;
import java.util.concurrent.CancellationException;

public interface CarteiraService {

    Carteira salvar(Carteira carteira);

    Carteira editar(Long id, Carteira carteira);

    Boolean excluir(Carteira carteira);




}
