package io.github.pameladilly.service;

import io.github.pameladilly.domain.entity.Ativo;
import io.github.pameladilly.domain.entity.RendaFixa;
import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.domain.repository.AtivoRepository;
import io.github.pameladilly.exception.ativo.AtivoNotFound;
import io.github.pameladilly.service.impl.AtivoServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(value = "test")
public class AtivoServiceTest {

    AtivoService service;

    @MockBean
    AtivoRepository repository;

    @BeforeEach
    public void setUp(){

        this.service = new AtivoServiceImpl(repository);
    }

    @Test
    @DisplayName("Encontrar ativo por Id e Usuário")
    public void encontrarAtivoPorIdeUsuario(){
        Usuario usuario = UsuarioServiceTest.createNewUsuario();
        usuario.setIdUsuario(3L);

        RendaFixa rendaFixaMock = RendaFixaServiceTest.newRendaFixa(usuario, LocalDate.of(2025, 12, 1));
        rendaFixaMock.setIdAtivo(1L);

        Mockito.when( repository.findByIdAndUsuario(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.of( rendaFixaMock));

        Ativo ativoEncontrado = service.findByIdAndUsuario(1L, 3L);

        Assertions.assertThat(ativoEncontrado).isNotNull();
        Assertions.assertThat(ativoEncontrado.getIdAtivo()).isEqualTo(1L);
        Assertions.assertThat(ativoEncontrado.getUsuario()).isEqualTo(usuario);


    }

    @Test
    @DisplayName("Lançar exceção ao não encontrar ativo por Id e Usuário")
    public void naoEncontrarAtivoPorIdEUsuario(){

        Mockito.when( repository.findByIdAndUsuario(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.empty() );

        org.junit.jupiter.api.Assertions.assertThrows(AtivoNotFound.class, () -> service.findByIdAndUsuario(1L, 3L));


    }
}
