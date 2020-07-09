package io.github.pameladilly.service.impl;

import io.github.pameladilly.domain.entity.Ativo;
import io.github.pameladilly.domain.repository.AtivoRepository;
import io.github.pameladilly.exception.ativo.AtivoNotFound;
import io.github.pameladilly.service.AtivoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtivoServiceImpl implements AtivoService {

    private final AtivoRepository repository;


    @Override
    public Ativo findByIdAndUsuario(Long id, Long usuario) {

        return repository.findByIdAndUsuario(id, usuario).orElseThrow(AtivoNotFound::new);
    }
}
