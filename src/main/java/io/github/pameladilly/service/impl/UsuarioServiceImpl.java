package io.github.pameladilly.service.impl;

import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.domain.repository.Usuarios;
import io.github.pameladilly.exception.usuario.SenhasNaoConferemException;
import io.github.pameladilly.exception.usuario.SenhaInvalidaException;
import io.github.pameladilly.exception.usuario.UsuarioNotFoundException;
import io.github.pameladilly.rest.dto.UsuarioRequestDTO;
import io.github.pameladilly.service.UsuarioService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsuarioServiceImpl implements UsuarioService {

  //  @Autowired
    final Usuarios repository;

    public UsuarioServiceImpl(Usuarios repository) {
        this.repository = repository;
    }

    @Override
    public Usuario salvar(UsuarioRequestDTO usuarioRequestDTO)    {
        if(!usuarioRequestDTO.getSenha().equals(usuarioRequestDTO.getSenhaConfirmacao())) {
            throw new SenhasNaoConferemException();
        }

        Usuario usuario = converter(usuarioRequestDTO);
        usuario.setDataCadastro(  LocalDateTime.now() );
        return repository.save(usuario);

    }

    @Override
    public Usuario atualizar(Long id, UsuarioRequestDTO usuarioRequestDTO) {
        if(!usuarioRequestDTO.getSenha().equals(usuarioRequestDTO.getSenhaConfirmacao())) {
            throw new SenhasNaoConferemException();
        }
        Usuario usuario = repository.findById(id).map(
                user -> {
                    user.setSenha(usuarioRequestDTO.getSenha());
                    user.setNome(usuarioRequestDTO.getNome());
                    user.setEmail(usuarioRequestDTO.getEmail());
                    user.setLogin(usuarioRequestDTO.getLogin());

                    return user;
                }).orElseThrow(
                    UsuarioNotFoundException::new
        );

        return repository.save(usuario);

    }


    @Override
    public Boolean alterarSenha(Long id, String senha, String senhaConfirmacao) {
        if (!senha.equals(senhaConfirmacao)) {
            throw new SenhasNaoConferemException();
        }

        Usuario usuario = repository.findById(id).orElseThrow( UsuarioNotFoundException::new);

        usuario.setSenha(senha);

        return (repository.save(usuario) != null);
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

    @Override
    public void excluir(Long id) {

         repository.deleteById(id);

    }

    @Override
    public Usuario getUsuarioById(Long id) {

        return repository.findById(id).orElseThrow(UsuarioNotFoundException::new);
    }


    private Usuario converter(UsuarioRequestDTO usuarioRequestDTO) {
        return Usuario.builder()
                    .email(usuarioRequestDTO.getEmail())
                    .nome(usuarioRequestDTO.getNome())
                    .login(usuarioRequestDTO.getLogin())
                    .senha(usuarioRequestDTO.getSenha())
                    .build();
    }
}
