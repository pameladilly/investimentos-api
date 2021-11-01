package io.github.pameladilly.repository;

import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.domain.repository.UsuarioRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class UsuariosTest {


    @Autowired
    UsuarioRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    @DisplayName("Deve salvar um usuário")
    public void salvarUmUsuario(){


        Usuario usuario = createUsuario();

        entityManager.persist(usuario);

        Boolean usuarioSalvo = repository.findByLogin(usuario.getLogin()).isPresent();


        assertThat(usuarioSalvo).isTrue();



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
    @DisplayName("Deve carregar um usuário a partir do login e validar a senha.")
    public void carregarUsuarioLoginSenha(){
        Usuario usuario = createUsuario();

        String login  = "usuario@usuario";
        usuario.setLogin(login);
        entityManager.persist(usuario);
        Usuario usuarioEncontrado = repository.findByLogin(login).get();


        Assertions.assertThat(usuarioEncontrado).isNotNull();


    }

    @Test
    @DisplayName("Deve deletar um usuário")
    public void excluirUsuario(){

        Usuario usuario = createUsuario();

        entityManager.persist(usuario);

        Usuario usuarioEncontrado = entityManager.find(Usuario.class, usuario.getIdUsuario());

        repository.delete(usuarioEncontrado);

        Usuario usuarioExcluido = entityManager.find(Usuario.class, usuarioEncontrado.getIdUsuario());

        Assertions.assertThat(usuarioExcluido).isNull();
    }
}
