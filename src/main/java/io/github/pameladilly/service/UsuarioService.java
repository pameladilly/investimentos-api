package io.github.pameladilly.service;

import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.rest.dto.UsuarioDTO;

public interface UsuarioService {

    Usuario salvar(UsuarioDTO usuarioDTO);

    Usuario atualizar(Integer idUsuario, UsuarioDTO usuarioDTO);

    Boolean alterarSenha(String senha, String senhaConfirmacao);

    Usuario carregar(String login, String senha);
}
