package io.github.pameladilly.service;


import io.github.pameladilly.domain.entity.Carteira;
import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.domain.repository.Carteiras;
import io.github.pameladilly.domain.repository.Usuarios;
import io.github.pameladilly.exception.usuario.UsuarioNotFoundException;
import io.github.pameladilly.service.impl.CarteiraServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CarteiraServiceTest {

    CarteiraService service;


    @MockBean
    Carteiras repository;

    @MockBean
    Usuarios usuariosRepository;

    @BeforeEach
    public void setUp(){
        this.service = new CarteiraServiceImpl(repository, usuariosRepository);
    }

    @Test
    @DisplayName("Deve salvar uma carteira")
    public void salvarCarteiraTest(){

        Usuario usuario = getNewUsuario();

        Carteira carteiraSalvar = Carteira.builder().usuario(usuario).descricao("Renda Fixa Test").build();
        Carteira carteiraSalva = Carteira.builder()
                .idCarteira(1L)
                .dataCadastro(LocalDateTime.now())
                .usuario(usuario).descricao("Renda Fixa Test").build();

        Mockito.when( usuariosRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when( repository.save(carteiraSalvar)).thenReturn(carteiraSalva);

        Carteira carteira = service.salvar(carteiraSalvar);

        Assertions.assertThat(carteira.getIdCarteira()).isNotNull();
        Assertions.assertThat(carteira.getUsuario()).isEqualTo(usuario);
        Assertions.assertThat(carteira.getDataCadastro()).isNotNull();
        Assertions.assertThat(carteira.getDescricao()).isEqualTo(carteiraSalva.getDescricao());
    }

    private Usuario getNewUsuario() {
        return Usuario.builder().idUsuario(1L).senha("123").email("usuario@email.com").login("usuario.test")
                .nome("Test").build();
    }

    @Test
    @DisplayName("NÃO deve salvar uma carteira sem usuário ")
    public void naoSalvarCarteiraSemUsuarioTest(){

        Usuario usuario = getNewUsuario();

        Carteira carteiraSalvar = Carteira.builder().idCarteira(1L).descricao("Renda Fixa Test").build();
        carteiraSalvar.setUsuario(usuario);


        Mockito.when( usuariosRepository.existsById(Mockito.anyLong())).thenReturn(false);

        Throwable exception = Assertions.catchThrowable(() -> service.salvar(carteiraSalvar));

        Assertions.assertThat(exception).isInstanceOf(UsuarioNotFoundException.class)
                .hasMessage( UsuarioNotFoundException.MSG);

        Mockito.verify( repository, Mockito.never()).save(carteiraSalvar);
    }

}
