package io.github.pameladilly.service;

import io.github.pameladilly.domain.entity.RendaVariavel;
import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.domain.enums.TipoAtivo;
import io.github.pameladilly.domain.repository.RendaVariavelRepository;
import io.github.pameladilly.exception.rendavariavel.RendaVariavelNotFound;
import io.github.pameladilly.service.impl.RendaVariavelImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@ActiveProfiles(value = "test")
@ExtendWith(SpringExtension.class)
public class RendaVariavelServiceTest {

    @MockBean
    RendaVariavelRepository repository;

    RendaVariavelService service;

    @BeforeEach
    public void setUp(){

        service = new RendaVariavelImpl(repository);
    }

    @Test
    @DisplayName("Salvar renda variavel")
    public void salvarRendaVariavel(){

        Usuario usuario = UsuarioServiceTest.createNewUsuario();
        usuario.setIdUsuario(1L);

        RendaVariavel rendaVariavel = createRendaVariavel(usuario);

        Mockito.when( repository.save( Mockito.any(RendaVariavel.class))).thenReturn(rendaVariavel);

        RendaVariavel rendaVariavelSalva = service.salvar(rendaVariavel);

        Mockito.verify( repository, Mockito.times(1)).save(rendaVariavel);
        Assertions.assertThat(rendaVariavelSalva).isEqualTo(rendaVariavel);


    }

    public static RendaVariavel createRendaVariavel(Usuario usuario) {
        return new RendaVariavel(1L,
                "Weg S.A.",
                LocalDateTime.now(),
                LocalDateTime.now(),
                TipoAtivo.RENDAVARIAVEL,
                usuario,
                "WEGE3",
                BigDecimal.valueOf(61.47));
    }

    @Test
    @DisplayName("Atualizar renda variável")
    public void atualizarRendaVariavel(){

        Usuario usuario = UsuarioServiceTest.createNewUsuario();
        usuario.setIdUsuario(1L);

        RendaVariavel rendaVariavel = createRendaVariavel(usuario);

        Mockito.when( repository.findById(Mockito.anyLong())).thenReturn( Optional.of(rendaVariavel));
        Mockito.when( repository.save(Mockito.any(RendaVariavel.class))).thenReturn(rendaVariavel);

        RendaVariavel rendaVariavelAtualizada = service.atualizar(rendaVariavel);

        Mockito.verify( repository, Mockito.times(1)).save(rendaVariavel);
        Assertions.assertThat(rendaVariavelAtualizada).isEqualTo(rendaVariavel);

    }

    @Test
    @DisplayName("Atualizar renda variavel inexistente")
    public void atualizarRendaVariavelInexistente(){

        Usuario usuario = UsuarioServiceTest.createNewUsuario();
        usuario.setIdUsuario(1L);

        RendaVariavel rendaVariavel = createRendaVariavel(usuario);

        Mockito.when( repository.findById(Mockito.anyLong())).thenReturn( Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(RendaVariavelNotFound.class, () -> service.atualizar(rendaVariavel) );

        Mockito.verify( repository, Mockito.never()).save(rendaVariavel);
    }

    @Test
    @DisplayName("Atualizar cotação")
    public void atualizarCotacao(){

        Usuario usuario = UsuarioServiceTest.createNewUsuario();
        usuario.setIdUsuario(1L);

        BigDecimal cotacao = BigDecimal.valueOf(89.98);

        RendaVariavel rendaVariavel = createRendaVariavel(usuario);
        rendaVariavel.setIdAtivo(1L);
        rendaVariavel.setCotacao(cotacao);

        Mockito.when( repository.findById(Mockito.anyLong())).thenReturn(Optional.of(rendaVariavel));
        Mockito.when( repository.save(Mockito.any(RendaVariavel.class))).thenReturn(rendaVariavel);

        RendaVariavel rendaVariavelCotacao = service.atualizarCotacao(1L, cotacao);

        Assertions.assertThat(rendaVariavelCotacao.getCotacao()).isEqualTo(cotacao);
        Mockito.verify( repository, Mockito.times(1)).save(Mockito.any(RendaVariavel.class));

    }

    @Test
    @DisplayName("Atualizar cotação renda variavel inexisten")
    public void atualizarCotacaoRendaVarivelInexisten(){
        Usuario usuario = UsuarioServiceTest.createNewUsuario();
        usuario.setIdUsuario(1L);

        BigDecimal cotacao = BigDecimal.valueOf(89.98);

        RendaVariavel rendaVariavel = createRendaVariavel(usuario);
        rendaVariavel.setIdAtivo(1L);
        rendaVariavel.setCotacao(cotacao);

        Mockito.when( repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when( repository.save(Mockito.any(RendaVariavel.class))).thenReturn(rendaVariavel);

        org.junit.jupiter.api.Assertions.assertThrows( RendaVariavelNotFound.class , () -> service.atualizarCotacao(1L, cotacao));

        Mockito.verify( repository, Mockito.never()).save( Mockito.any(RendaVariavel.class));

    }

    @Test
    @DisplayName("Excluir por Id")
    public void excluirPorId(){

        Usuario usuario = UsuarioServiceTest.createNewUsuario();
        usuario.setIdUsuario(1L);
        RendaVariavel rendaVariavel = createRendaVariavel(usuario);
        rendaVariavel.setIdAtivo(1L);

        Mockito.when( repository.findById(Mockito.anyLong())).thenReturn(Optional.of(rendaVariavel));

        org.junit.jupiter.api.Assertions.assertDoesNotThrow( () -> service.excluir(1L));

        Mockito.verify( repository, Mockito.times(1)).delete(Mockito.any(RendaVariavel.class));
    }

    @Test
    @DisplayName("Lançar exceção ao excluir renda variavel inexistente por Id ")
    public void excluirPorIdInexistente(){

        Mockito.when( repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows( RendaVariavelNotFound.class , () -> service.excluir(1L));

        Mockito.verify( repository, Mockito.never()).delete(Mockito.any(RendaVariavel.class));

    }

    @Test
    @DisplayName("Excluir por objeto")
    public void excluirPorObjeto(){
        Usuario usuario = UsuarioServiceTest.createNewUsuario();
        usuario.setIdUsuario(1L);
        RendaVariavel rendaVariavel = createRendaVariavel(usuario);
        rendaVariavel.setIdAtivo(1L);

        Mockito.when( repository.findById(Mockito.anyLong())).thenReturn(Optional.of(rendaVariavel));

        org.junit.jupiter.api.Assertions.assertDoesNotThrow( () -> service.excluir(rendaVariavel));

        Mockito.verify( repository, Mockito.times(1)).delete(rendaVariavel);
    }

    @Test
    @DisplayName("Lançar exceção ao excluir renda varíavel")
    public void excluirPorObjetoInexistente(){

    }

    @Test
    @DisplayName("Encontrar por Id")
    public void encontrarPorId(){

    }

    @Test
    @DisplayName("Lançar exceção ao não encontrar por Id")
    public void lancarExcecaoAoNaoEncontrarPorId(){

    }

    @Test
    @DisplayName("Existe por id")
    public void existePorId(){

    }

    @Test
    @DisplayName("Pesquisar")
    public void pesquisar(){

    }

}
