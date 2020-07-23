package io.github.pameladilly.service;

import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.domain.repository.UsuarioRepository;
import io.github.pameladilly.exception.usuario.SenhaInvalidaException;
import io.github.pameladilly.exception.usuario.SenhasNaoConferemException;
import io.github.pameladilly.exception.usuario.UsuarioNotFoundException;
import io.github.pameladilly.rest.dto.UsuarioRequestDTO;
import io.github.pameladilly.rest.dto.UsuarioResponseDTO;
import io.github.pameladilly.service.impl.UsuarioServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AssertionsKt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @MockBean
    UsuarioRepository repository;

    UsuarioService service;

    @BeforeEach
    public void setUp(){
        this.service = new UsuarioServiceImpl( repository );
    }


    @Test
    @DisplayName("Deve salvar um usuário")
    public void savarUsuario(){

        Usuario usuario = Usuario.builder()

                .senha("123")
                .login("outro")
                .email("usuario@email.com")
                .nome("Rafaela")
                .build();

        Mockito.when( repository.save( Mockito.any()))
                .thenReturn( createNewUsuario() );

        Usuario usuarioSalvo = service.salvar(usuario, "123");

        Assertions.assertThat(usuarioSalvo.getIdUsuario()).isNotNull();
        Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("Rafaela");
        Assertions.assertThat(usuarioSalvo.getDataCadastro()).isNotNull();
        Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo(usuario.getEmail());



    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar salvar usuário com senhas diferentes")
    public void senhasNaoBatem(){
        Usuario usuario = createNewUsuario();
        String senhaConfirmacao = "456";

        org.junit.jupiter.api.Assertions
                .assertThrows(SenhasNaoConferemException.class, () -> service.salvar(usuario, senhaConfirmacao));

        Mockito.verify( repository, Mockito.never()).save( usuario );

    }

    static public Usuario createNewUsuario() {
        return Usuario.builder()
                .idUsuario(1L)
                .dataCadastro(LocalDateTime.now())
                .senha("123")
                .login("outro")
                .email("usuario@email.com")
                .nome("Rafaela")
                .build();
    }

    @Test
    @DisplayName("Atualizar usuário")
    public void atualizarUsuario(){

        Usuario usuario = Usuario.builder()

                .senha("123")
                .login("outro")
                .email("usuario@email.com")
                .nome("Rafaela")
                .build();


        Mockito.when( repository.findById(Mockito.anyLong())).thenReturn( Optional.of(usuario));
        Mockito.when( repository.save( Mockito.any()))
                .thenReturn( createNewUsuario() );

        Usuario usuarioAtualizado = service.atualizar(1L, usuario, "123");

        Assertions.assertThat(usuarioAtualizado.getIdUsuario()).isNotNull();



    }

    @Test
    @DisplayName("Carregar usuário")
    public void carregarUsuario(){

        Usuario usuario = createNewUsuario();
        usuario.setIdUsuario(1L);

        String login = "teste@teste";

        usuario.setLogin(login);

        String senha = "123";

        usuario.setSenha(senha);

        Mockito.when( repository.findByLogin(Mockito.anyString())).thenReturn( Optional.of( usuario) );

        Usuario usuarioFind = service.carregar(login, senha);


        Assertions.assertThat(usuarioFind).isNotNull();

    }

    @Test
    @DisplayName("Lançar exceção ao tentar carregar usuário com senha que não confere")
    public void carregarUsuarioSenhaInvalida(){

        Usuario usuario = createNewUsuario();
        usuario.setIdUsuario(1L);

        String login = "teste@teste";

        usuario.setLogin(login);

        String senha = "123";

        usuario.setSenha(senha);

        Mockito.when( repository.findByLogin(Mockito.anyString())).thenReturn( Optional.of( usuario) );


        org.junit.jupiter.api.Assertions.assertThrows( SenhaInvalidaException.class, () -> service.carregar(login, "4560"));

        Mockito.verify( repository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @DisplayName("Alterar senha")
    public void alterarSenha(){

        Usuario usuario = createNewUsuario();
        usuario.setIdUsuario(1L);
        usuario.setSenha("123");

        Mockito.when( repository.findById(Mockito.anyLong())).thenReturn( Optional.of( usuario) );
        Mockito.when( repository.save( Mockito.any(Usuario.class))).thenReturn( usuario );

        Boolean senhaAlterada = service.alterarSenha(1L, "456", "456");

        Assertions.assertThat(senhaAlterada).isTrue();


    }

    @Test
    @DisplayName("Lançar exceção por alterar senhas que não batem")
    public void alterarSenhaNaoBatem(){

        Usuario usuario = createNewUsuario();
        usuario.setIdUsuario(1L);
        usuario.setSenha("123");


        Throwable exception = Assertions.catchThrowable( () -> service.alterarSenha(1L, "123", "1234"));


        Assertions.assertThat(exception).isInstanceOf(SenhasNaoConferemException.class);

        Mockito.verify( repository, Mockito.never()).save(Mockito.any());

    }

    @Test
    @DisplayName("Excluir usuário ")
    public void excluirUsuario(){

        Usuario usuario = createNewUsuario();
        Mockito.when( repository.findById(Mockito.anyLong())).thenReturn(Optional.of(usuario));


        org.junit.jupiter.api.Assertions.assertDoesNotThrow( () -> service.excluir(1L) );

        Mockito.verify( repository, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @DisplayName("Excluir usuário inexistente")
    public void excluirUsuarioInexistente(){


        org.junit.jupiter.api.Assertions.assertThrows( UsuarioNotFoundException.class, () -> service.excluir(1L));

        Mockito.verify( repository, Mockito.never()).delete(Mockito.any());

    }


}
