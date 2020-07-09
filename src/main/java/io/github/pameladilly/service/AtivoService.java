package io.github.pameladilly.service;

import io.github.pameladilly.domain.entity.Ativo;

public interface AtivoService {

    Ativo findByIdAndUsuario(Long id, Long usuario);
}
