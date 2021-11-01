package io.github.pameladilly.rest.controller;

import io.github.pameladilly.domain.entity.Carteira;
import io.github.pameladilly.domain.entity.RendaVariavel;
import io.github.pameladilly.domain.entity.Transacao;
import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.domain.enums.TipoTransacao;
import io.github.pameladilly.domain.repository.TransacaoRepository;
import io.github.pameladilly.exception.ativo.AtivoNotFound;
import io.github.pameladilly.exception.carteira.CarteiraNotFound;
import io.github.pameladilly.rest.dto.TransacaoRequestDTO;
import io.github.pameladilly.service.*;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WebMvcTest(controllers = TransacaoController.class)
public class TransacaoControllerTest {

    static final private String API_TRANSACAO = "/api/transacao";


    @Autowired
    MockMvc mockMvc;

    @MockBean
    TransacaoRepository repository;

    @MockBean
    TransacaoService service;

    @MockBean
    UsuarioService usuarioService;

    @MockBean
    AtivoService ativoService;

    @MockBean
    CarteiraService carteiraService;

    @Test
    @DisplayName("/API/TRANSACAO - POST - Salvar uma transação")
    public void salvar() throws Exception{


        TransacaoRequestDTO transacaoRequestDTO = getNewTransacaoCompra();

        Transacao transacaoSalva = getNewTransacao(transacaoRequestDTO.getQuantidade(), transacaoRequestDTO.getValorUnitario());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tipoTransacao", transacaoRequestDTO.getTipoTransacao());
        jsonObject.put("valorUnitario", transacaoRequestDTO.getValorUnitario());
        jsonObject.put("data", "07/08/2020");
        jsonObject.put("quantidade", transacaoRequestDTO.getQuantidade());
        jsonObject.put("carteira", transacaoRequestDTO.getCarteira());
        jsonObject.put("usuario", transacaoRequestDTO.getUsuario());
        jsonObject.put("ativo", transacaoRequestDTO.getAtivo());

        BDDMockito.given( service.salvar(Mockito.any(Transacao.class))).willReturn( transacaoSalva );

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API_TRANSACAO)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString());

        mockMvc.perform( request ).andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    @DisplayName("/API/TRANSACAO - POST - Lançar not found por não encontrar ativo para o usuário indicado")
    public void naoSalvarUsuarioInexistente() throws Exception{

        TransacaoRequestDTO transacaoRequestDTO = getNewTransacaoCompra();


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tipoTransacao", transacaoRequestDTO.getTipoTransacao());
        jsonObject.put("valorUnitario", transacaoRequestDTO.getValorUnitario());
        jsonObject.put("data", "07/08/2020");
        jsonObject.put("quantidade", transacaoRequestDTO.getQuantidade());
        jsonObject.put("carteira", transacaoRequestDTO.getCarteira());
        jsonObject.put("usuario", 2);
        jsonObject.put("ativo", 3);

        BDDMockito.given( ativoService.findByIdAndUsuario(Mockito.anyLong(), Mockito.anyLong())).willThrow( new AtivoNotFound());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API_TRANSACAO)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString());

        mockMvc.perform( request ).andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect( MockMvcResultMatchers.jsonPath( "errors[0]", Matchers.containsStringIgnoringCase ("Ativo não encontrado")));

    }

    @Test
    @DisplayName("/API/TRANSACAO - POST - Lançar not found por carteira inexistente")
    public void naoSalvarCarteiraInexistente() throws Exception{

        TransacaoRequestDTO transacaoRequestDTO = getNewTransacaoCompra();


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tipoTransacao", transacaoRequestDTO.getTipoTransacao());
        jsonObject.put("valorUnitario", transacaoRequestDTO.getValorUnitario());
        jsonObject.put("data", "07/08/2020");
        jsonObject.put("quantidade", transacaoRequestDTO.getQuantidade());
        jsonObject.put("carteira", transacaoRequestDTO.getCarteira());
        jsonObject.put("usuario", 2);
        jsonObject.put("ativo", 3);

        BDDMockito.given( carteiraService.findById(Mockito.anyLong())).willThrow( new CarteiraNotFound());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API_TRANSACAO)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString());

        mockMvc.perform( request ).andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect( MockMvcResultMatchers.jsonPath( "errors[0]", Matchers.containsStringIgnoringCase ("Carteira não encontrada")));

    }

    @Test
    @DisplayName("/API/TRANSACAO - POST - Lançar bad request por data formatada incorretamente")
    public void naoSalvarDataFormatadaIncorretamente() throws Exception{

        TransacaoRequestDTO transacaoRequestDTO = getNewTransacaoCompra();


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tipoTransacao", transacaoRequestDTO.getTipoTransacao());
        jsonObject.put("valorUnitario", transacaoRequestDTO.getValorUnitario());
        jsonObject.put("data", "07-08-2020");
        jsonObject.put("quantidade", transacaoRequestDTO.getQuantidade());
        jsonObject.put("carteira", transacaoRequestDTO.getCarteira());
        jsonObject.put("usuario", 2);
        jsonObject.put("ativo", 3);


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API_TRANSACAO)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString());

        mockMvc.perform( request ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect( MockMvcResultMatchers.jsonPath( "errors[0]", Matchers.containsStringIgnoringCase("Formatação de dados inválida")));
    }

    @Test
    @DisplayName("API/TRANSACAO - POST - Retornar bad request por campos não informados")
    public void naoSalvarCamposNaoInformado() throws Exception{

        JSONObject jsonObject = new JSONObject();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API_TRANSACAO)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString());

        mockMvc.perform( request ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect( MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize(7)));
    }

    public Transacao getNewTransacao(BigDecimal quantidade, BigDecimal valorUnitario) {


        Usuario newUsuario = UsuarioServiceTest.createNewUsuario();

        RendaVariavel rendaVariavel = RendaVariavelServiceTest.createRendaVariavel(newUsuario);

        Carteira carteira = CarteiraServiceTest.newCarteira(newUsuario);

        return Transacao.builder().idTransacao(1L)
                .tipoTransacao(TipoTransacao.COMPRA)
                .ativo(rendaVariavel)
                .carteira(carteira)
                .quantidade(quantidade)
                .valorUnitario(valorUnitario)
                .total(valorUnitario.multiply(quantidade))
                .data(LocalDate.now())
                .dataAtualizacao(LocalDateTime.now())
                .dataCadastro(LocalDateTime.now())
                .build();
    }

    public TransacaoRequestDTO getNewTransacaoCompra() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String data = LocalDate.now().format(formatter);

        return TransacaoRequestDTO.builder().tipoTransacao("COMPRA").ativo(1L).carteira(1L).quantidade(BigDecimal.valueOf(45.00))
                .data( LocalDate.parse(data, formatter)).valorUnitario(BigDecimal.valueOf(34.76)).usuario(1L).build();
    }

}
