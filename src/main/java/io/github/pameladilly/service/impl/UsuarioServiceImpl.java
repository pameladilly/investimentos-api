package io.github.pameladilly.service.impl;

import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.domain.repository.UsuarioRepository;
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
    final UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario salvar(Usuario usuario, String confirmacaoSenha)    {
        if(!usuario.getSenha().equals(confirmacaoSenha)) {
            throw new SenhasNaoConferemException();
        }

        usuario.setDataCadastro(  LocalDateTime.now() );

        return repository.save(usuario);

    }

    @Override
    public Usuario atualizar(Long id, Usuario usuario, String confirmacaoSenha) {
        if(!usuario.getSenha().equals(confirmacaoSenha)) {
            throw new SenhasNaoConferemException();
        }
        Usuario usuarioUpdate = repository.findById(id).map(
                user -> {
                    user.setSenha(usuario.getSenha());
                    user.setNome(usuario.getNome());
                    user.setEmail(usuario.getEmail());
                    user.setLogin(usuario.getLogin());

                    return user;
                }).orElseThrow(
                   UsuarioNotFoundException::new
        );

        return repository.save(usuarioUpdate);

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

         Usuario usuario = repository.findById(id)
                 .orElseThrow(UsuarioNotFoundException::new);

         repository.delete(usuario);

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
