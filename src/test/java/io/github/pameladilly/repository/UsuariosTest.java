package io.github.pameladilly.repository;

import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.domain.repository.Usuarios;
import io.github.pameladilly.rest.dto.UsuarioRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class UsuariosTest {


    @Autowired
    Usuarios repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    @DisplayName("Deve salvar um usuário")
    public void salvarUmUsuario(){


        Usuario usuario = createUsuario();

        entityManager.persist(usuario);


        Optional<Usuario> usuarioSalvo = repository.findByLogin(usuario.getLogin());


        assertThat(usuarioSalvo.isPresent()).isTrue();



    }

    private Usuario createUsuario() {
        return Usuario.builder()
                .email("usuario@email.com")
                .login("usuario.usuario")
                .senha("123")
                .nome("Pamela")
                .dataCadastro( LocalDateTime.now() )
                .build();
    }

    @Test
    @DisplayName("Deve atualizar um usuário a partir do ID")
    public void atualizar(Integer idUsuario, UsuarioRequestDTO usuarioRequestDTO){


    }

    @Test
    @DisplayName("Deve alterar uma senha")
    public void alterarSenha(String senha, String senhaConfirmacao){

    }

    @Test
    @DisplayName("Deve carregar um usuário a partir do login e senha.")
    public void carregar(String login, String senha){

    }
}
