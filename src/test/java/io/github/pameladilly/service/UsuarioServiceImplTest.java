package io.github.pameladilly.service;

import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.domain.repository.Usuarios;
import io.github.pameladilly.service.impl.UsuarioServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceImplTest {

    @MockBean
    Usuarios repository;

    UsuarioService service;

    @BeforeEach
    public void setUp(){
        this.service = new UsuarioServiceImpl( repository );
    }

    public void savarUsuario(){
        Usuario usuario = Usuario.builder()
                .dataCadastro(LocalDateTime.now())
                .senha("123")
                .login("usuario")
                .email("email")
                .nome("Pamela")
                .build();

        Mockito.when( repository.save( usuario))
                .thenReturn( Usuario.builder()
                        .dataCadastro(LocalDateTime.now())
                        .senha("123")
                        .login("outro")
                        .email("usuario@email.com")
                        .nome("Rafaela")
                        .build());

        //Usuario savedUsuario = service.salvar( usuario );



    }
}
