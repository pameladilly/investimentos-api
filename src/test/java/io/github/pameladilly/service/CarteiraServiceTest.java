package io.github.pameladilly.service;


import io.github.pameladilly.domain.entity.Carteira;
import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.domain.repository.CarteiraRepository;
import io.github.pameladilly.domain.repository.UsuarioRepository;
import io.github.pameladilly.exception.usuario.UsuarioNotFoundException;
import io.github.pameladilly.service.impl.CarteiraServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CarteiraServiceTest {

    CarteiraService service;


    @MockBean
    CarteiraRepository repository;

    @MockBean
    UsuarioRepository usuariosRepository;

    @BeforeEach
    public void setUp(){
        this.service = new CarteiraServiceImpl(repository, usuariosRepository);
    }

    @Test
    @DisplayName("Deve salvar uma carteira")
    public void salvarCarteiraTest(){

        Usuario usuario = getNewUsuario();

        Carteira carteiraSalvar = Carteira.builder().usuario(usuario).descricao("Renda Fixa Test").build();
        Carteira carteiraSalva = newCarteira(usuario);

        Mockito.when( usuariosRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when( usuariosRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(usuario));
        Mockito.when( repository.save(carteiraSalvar)).thenReturn(carteiraSalva);

        Carteira carteira = service.salvar(carteiraSalvar);

        Assertions.assertThat(carteira.getIdCarteira()).isNotNull();
        Assertions.assertThat(carteira.getUsuario()).isEqualTo(usuario);
        Assertions.assertThat(carteira.getDataCadastro()).isNotNull();
        Assertions.assertThat(carteira.getDescricao()).isEqualTo(carteiraSalva.getDescricao());
    }

    private Carteira newCarteira(Usuario usuario) {
        return Carteira.builder()
                .idCarteira(1L)
                .dataCadastro(LocalDateTime.now())
                .usuario(usuario).descricao("Renda Fixa Test").build();
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

    @Test
    @DisplayName("Deve filtrar carteiras pelas propriedades")
    public void pesquisarCarteiraTest(){
        Carteira carteira = newCarteira(null);
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Carteira> lista = Arrays.asList(carteira);

        Page<Carteira> page = new PageImpl<Carteira>(lista, PageRequest.of(0, 10), 1);

        Mockito.when( repository.findAll(Mockito.any(Example.class), Mockito.any(PageRequest.class))).thenReturn(page);

        Page<Carteira> result = service.pesquisar(carteira, pageRequest);

        Assertions.assertThat(result.getTotalElements()).isEqualTo(1);
        Assertions.assertThat(result.getContent()).isEqualTo(lista);
        Assertions.assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        Assertions.assertThat(result.getPageable().getPageSize()).isEqualTo(10);

    }

}
