package io.github.pameladilly.service;

import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.rest.dto.UsuarioRequestDTO;

public interface UsuarioService {

    Usuario salvar(UsuarioRequestDTO usuarioRequestDTO);

    Usuario atualizar(Long id, UsuarioRequestDTO usuarioRequestDTO);

    Boolean alterarSenha(Long id, String senha, String senhaConfirmacao);

    Usuario carregar(String login, String senha);

    void excluir(Long id);

    Usuario getUsuarioById(Long id);

}
