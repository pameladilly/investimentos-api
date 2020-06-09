package io.github.pameladilly.service.impl;

import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.domain.repository.Usuarios;
import io.github.pameladilly.exception.usuario.SenhasNaoConferemException;
import io.github.pameladilly.exception.usuario.SenhaInvalidaException;
import io.github.pameladilly.exception.usuario.UsuarioNotFoundException;
import io.github.pameladilly.rest.dto.UsuarioDTO;
import io.github.pameladilly.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private Usuarios repository;


    @Override
    public Usuario salvar(UsuarioDTO usuarioDTO) {
        if(!usuarioDTO.getSenha().equals(usuarioDTO.getSenhaConfirmacao())) {
            throw new SenhasNaoConferemException();
        }

        Usuario usuario = converter(usuarioDTO);
        usuario.setDataCadastro(  LocalDateTime.now() );
        return repository.save(usuario);

    }

    @Override
    public Usuario atualizar(Integer idUsuario, UsuarioDTO usuarioDTO) {
        if(!usuarioDTO.getSenha().equals(usuarioDTO.getSenhaConfirmacao())) {
            throw new SenhasNaoConferemException();
        }
        return repository.findById(idUsuario).map(
                user -> {
                    user.setSenha(usuarioDTO.getSenha());
                    user.setNome(usuarioDTO.getNome());
                    user.setEmail(usuarioDTO.getEmail());
                    user.setLogin(usuarioDTO.getLogin());

                    return user;
                }).orElseThrow(
                () ->new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado na base de dados.")
        );
    }


    @Override
    public Boolean alterarSenha(String senha, String senhaConfirmacao) {
        return null;
    }

    @Override
    public Usuario carregar(String login, String senha) {

     return  repository.findByLogin(login).map( usuario -> {
            if(!usuario.getSenha().equals(senha)) {
                throw new SenhaInvalidaException();
            }

            return usuario;
        }).orElseThrow(UsuarioNotFoundException::new
        );
    }

    private Usuario converter(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                    .email(usuarioDTO.getEmail())
                    .nome(usuarioDTO.getNome())
                    .login(usuarioDTO.getLogin())
                    .senha(usuarioDTO.getSenha())
                    .build();
    }
}
