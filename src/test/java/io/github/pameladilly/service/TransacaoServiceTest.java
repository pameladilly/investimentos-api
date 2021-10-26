package io.github.pameladilly.service;

import io.github.pameladilly.domain.entity.Transacao;
import io.github.pameladilly.domain.enums.TipoTransacao;
import io.github.pameladilly.domain.repository.RendaFixaRepository;
import io.github.pameladilly.domain.repository.RendaVariavelRepository;
import io.github.pameladilly.domain.repository.TransacaoRepository;
import io.github.pameladilly.exception.transacao.TransacaoNotFound;
import io.github.pameladilly.service.impl.RendaFixaImpl;
import io.github.pameladilly.service.impl.RendaVariavelImpl;
import io.github.pameladilly.service.impl.TransacaoServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ActiveProfiles(value = "test")
@ExtendWith(SpringExtension.class)
public class TransacaoServiceTest {
    TransacaoService service;
    RendaFixaService rendaFixaService;
    RendaVariavelService rendaVariavelService;

    @MockBean
    TransacaoRepository repository;

    @MockBean
    RendaFixaRepository rendaFixaRepository;

    @MockBean
    RendaVariavelRepository rendaVariavelRepository;

    @BeforeEach
    public void setUp(){
        rendaVariavelService = new RendaVariavelImpl(rendaVariavelRepository);
        rendaFixaService = new RendaFixaImpl(rendaFixaRepository);
        service = new TransacaoServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve salvar uma transação")
    public void salvar(){
        Transacao transacao = getNewTransacao();
        transacao.setIdTransacao(1L);

        BigDecimal total = transacao.getQuantidade().multiply(transacao.getValorUnitario());

        Mockito.when( repository.save(Mockito.any())).thenReturn( transacao);

        Transacao transacaoSalva = service.salvar(transacao);

        Assertions.assertThat(transacaoSalva).isNotNull();
        Assertions.assertThat(transacaoSalva.getIdTransacao()).isEqualTo(transacao.getIdTransacao());
        Assertions.assertThat(transacaoSalva.getAtivo()).isEqualTo(transacao.getAtivo());
        Assertions.assertThat(transacaoSalva.getData()).isEqualTo(transacao.getData());
        Assertions.assertThat(transacaoSalva.getTotal()).isEqualTo(total);

        Mockito.verify( repository, Mockito.times(1)).save(Mockito.any(Transacao.class));


    }

    @Test
    @DisplayName("Deve atualizar uma transação")
    public void atualizar(){

        Transacao transacao = getNewTransacao();
        BigDecimal valorUni = BigDecimal.valueOf(18.79);
        BigDecimal quantidade = BigDecimal.valueOf(37.00);

        transacao.setQuantidade(quantidade);
        transacao.setValorUnitario(valorUni);


        BigDecimal total = quantidade.multiply(valorUni);

        Mockito.when( repository.findById(Mockito.anyLong())).thenReturn(Optional.of(transacao));

        Mockito.when( repository.save(Mockito.any())).thenReturn( transacao );


        Transacao transacaoAtualizada = service.atualizar( transacao );

        Assertions.assertThat(transacaoAtualizada).isNotNull();
        Assertions.assertThat(transacaoAtualizada.getTotal()).isEqualTo(total);

        Mockito.verify( repository, Mockito.times(1)).save(Mockito.any(Transacao.class));

    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar transação inexistente")
    public void atualizarTransacaoInexistente(){
        Transacao transacao = getNewTransacao();

        Mockito.when( repository.findById(Mockito.any())).thenReturn( Optional.empty() );

        org.junit.jupiter.api.Assertions.assertThrows(TransacaoNotFound.class, () -> service.atualizar( transacao ));


        Mockito.verify( repository, Mockito.never()).save(Mockito.any(Transacao.class));
    }

    @Test
    @DisplayName("Excluir transação por objeto")
    public void excluir(){

        Transacao transacao = getNewTransacao();
        Mockito.when( repository.existsById(Mockito.anyLong())).thenReturn( true );

        org.junit.jupiter.api.Assertions.assertDoesNotThrow( () -> service.excluir(transacao));

        Mockito.verify( repository, Mockito.times(1)).delete(Mockito.any(Transacao.class));
    }

    @Test
    @DisplayName("Excluir transação inexistente por objeto")
    public void excluirTransacaoInexistente(){

        Transacao transacao = getNewTransacao();

        Mockito.when( repository.existsById(Mockito.anyLong())).thenReturn( false );

        org.junit.jupiter.api.Assertions.assertThrows( TransacaoNotFound.class, () -> service.excluir(transacao));

        Mockito.verify( repository, Mockito.never()).delete(Mockito.any(Transacao.class));

    }

    @Test
    @DisplayName("Exluir transacao por Id")
    public void excluirPorId(){

        Transacao transacao = getNewTransacao();

        Mockito.when( repository.findById(Mockito.anyLong())).thenReturn( Optional.of(transacao) );
        Mockito.when( repository.existsById(Mockito.anyLong())).thenReturn( true );

        org.junit.jupiter.api.Assertions.assertDoesNotThrow( () -> service.excluir(1L));

        Mockito.verify( repository, Mockito.times(1)).delete(Mockito.any(Transacao.class));
    }

    @Test
    @DisplayName("Excluir transação por Id inexistente")
    public void excluirPorIdInexistente(){
        Transacao transacao = getNewTransacao();

        Mockito.when( repository.findById(Mockito.anyLong())).thenReturn( Optional.empty() );

        org.junit.jupiter.api.Assertions.assertThrows (TransacaoNotFound.class, () -> service.excluir(1L));

        Mockito.verify( repository, Mockito.never()).delete(Mockito.any(Transacao.class));
    }

    @Test
    @DisplayName("Encontrar por Id")
    public void encontrarPorId(){

        Transacao transacao = getNewTransacao();

        Mockito.when( repository.findById(Mockito.anyLong())).thenReturn( Optional.of( transacao ) );

        Transacao transacaoEncontrada = service.findById(1L);

        Assertions.assertThat(transacaoEncontrada).isNotNull();
    }

    @Test
    @DisplayName("Lançar exceção ao não encontrar por Id")
    public void encontrarPorIdInexistente(){

        Mockito.when( repository.findById(Mockito.anyLong())).thenReturn( Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows( TransacaoNotFound.class, () -> service.findById(1L));

    }


    @Test
    @DisplayName("Pesquisar transação")
    public void pesquisar(){

        Transacao transacao = getNewTransacao();

        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Transacao> lista = Arrays.asList(transacao);
        Page<Transacao> page = new PageImpl<>(lista, PageRequest.of(0, 10), 1);

        Mockito.when( repository.findAll(Mockito.any(Example.class), Mockito.any(PageRequest.class))).thenReturn( page );

        Page<Transacao> result = service.pesquisar(transacao, pageRequest);

        Assertions.assertThat(result.getTotalElements()).isEqualTo(1L);
        Assertions.assertThat(result.getContent()).isEqualTo(lista);
        Assertions.assertThat(result.getPageable().getPageSize()).isEqualTo(10);
        Assertions.assertThat(result.getPageable().getPageNumber()).isEqualTo(0);


    }

    public Transacao getNewTransacao() {
        return Transacao.builder().valorUnitario(BigDecimal.valueOf(56.89)).idTransacao(1L)
                .quantidade(BigDecimal.valueOf(45.00)).carteira(CarteiraServiceTest.newCarteira(UsuarioServiceTest.createNewUsuario())).ativo(RendaVariavelServiceTest.createRendaVariavel(UsuarioServiceTest.createNewUsuario())).tipoTransacao(TipoTransacao.COMPRA).data(LocalDate.now()).build();
    }
}
