package io.github.pameladilly.service.impl;

import io.github.pameladilly.domain.entity.Carteira;
import io.github.pameladilly.domain.repository.CarteiraRepository;
import io.github.pameladilly.domain.repository.UsuarioRepository;
import io.github.pameladilly.exception.carteira.CarteiraNotFound;
import io.github.pameladilly.exception.usuario.UsuarioNotFoundException;
import io.github.pameladilly.service.CarteiraService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CarteiraServiceImpl implements CarteiraService {

    CarteiraRepository repository;
    UsuarioRepository usuariosRepository;

    public CarteiraServiceImpl(CarteiraRepository repository, UsuarioRepository usuariosRepository) {
        this.repository = repository;
        this.usuariosRepository = usuariosRepository;
    }

    @Override
    public Carteira salvar(Carteira carteira) {


        if(!usuariosRepository.existsById(carteira.getUsuario().getIdUsuario())){
            throw new UsuarioNotFoundException();
        }else {
            carteira.setUsuario( usuariosRepository.findById(carteira.getUsuario().getIdUsuario()).get());
        }




        return repository.save(carteira);
    }

    @Override
    public Carteira editar(Long id, Carteira carteira) {

        Carteira carteiraEncontrada = repository.findById(id).orElseThrow(
                () -> new CarteiraNotFound()
        );

        return repository.save(carteiraEncontrada);
    }

    @Override
    public Boolean excluir(Carteira carteira) {

        Carteira carteiraEncontrada = repository.findById(carteira.getIdCarteira()).orElseThrow(
                () -> new CarteiraNotFound()
        );

        repository.delete(carteiraEncontrada);

        return (!repository.findById(carteiraEncontrada.getIdCarteira()).isPresent());
    }

    @Override
    public Page<Carteira> pesquisar(Carteira filter, Pageable pageRequest) {

        Example<Carteira> example = Example.of(filter,
                ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher( ExampleMatcher.StringMatcher.CONTAINING));

        return repository.findAll(example, pageRequest);
    }
}
