package io.github.pameladilly.rest.controller;

import io.github.pameladilly.domain.entity.RendaFixa;
import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.domain.enums.TipoAtivo;
import io.github.pameladilly.exception.rendafixa.RendaFixaNotFound;
import io.github.pameladilly.exception.usuario.UsuarioNotFoundException;
import io.github.pameladilly.rest.dto.RendaFixaRequestDTO;
import io.github.pameladilly.service.RendaFixaService;
import io.github.pameladilly.service.UsuarioService;
import io.github.pameladilly.service.UsuarioServiceTest;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.Collections;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WebMvcTest(controllers = RendaFixaController.class)
public class RendaFixaControllerTest {

    static final String RENDAFIXA_API = "/api/rendafixa";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RendaFixaService rendaFixaService;

    @MockBean
    UsuarioService usuarioService;

    @Test
    @DisplayName("/API/RENDAFIXA - POST - Deve salvar um ativo de renda fixa")
    public void salvarRendaFixaTest() throws Exception{


        BigDecimal rentabilidadeDiaria = BigDecimal.valueOf(0.02);
        BigDecimal rentabilidadeMensal = BigDecimal.valueOf(0.65);
        LocalDate vencimento = LocalDate.of(2025, 12, 31);
        BigDecimal preco = BigDecimal.valueOf(1000.00);

        RendaFixaRequestDTO rendaFixaRequestDTO = RendaFixaRequestDTO.builder()
                .descricao("Tesouro Direto")
                .preco(preco)
                .rentabilidadeDiaria(rentabilidadeDiaria)
                .rentabilidadeMensal(rentabilidadeMensal)
                .vencimento(vencimento)
                .usuario(1L)
                .build();
        Usuario usuarioMock = UsuarioControllerTest.newUsuario();
        usuarioMock.setIdUsuario(1L);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("descricao", rendaFixaRequestDTO.getDescricao());
        jsonObject.put("rentabilidadeDiaria", rendaFixaRequestDTO.getRentabilidadeDiaria());
        jsonObject.put("vencimento", "01/01/2025");
        jsonObject.put("preco", rendaFixaRequestDTO.getPreco());
        jsonObject.put("rentabilidadeMensal", rendaFixaRequestDTO.getRentabilidadeMensal());
        jsonObject.put("usuario", rendaFixaRequestDTO.getUsuario());


        RendaFixa rendaFixaMock = new RendaFixa(1L, "Tesouro Direto", LocalDateTime.now(), null,
                TipoAtivo.RENDAFIXA, usuarioMock, rentabilidadeDiaria,  vencimento, preco, rentabilidadeMensal );

        BDDMockito.given( rendaFixaService.salvar(Mockito.any())).willReturn(rendaFixaMock);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(RENDAFIXA_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString());

        mockMvc.perform( request )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect( MockMvcResultMatchers.jsonPath( "id").value(1L))
                .andExpect( MockMvcResultMatchers.jsonPath( "dataCadastro").isNotEmpty());


    }

    @Test
    @DisplayName("/API/RENDAFIXA - POST - Deve retornar bad request por campo não informados")
    public void naoSalvarRendaFixaCamposNaoInformadosTest() throws Exception {
        RendaFixaRequestDTO rendaFixaRequestDTO = RendaFixaRequestDTO.builder().descricao("Tesouro Selic 2015").build();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("descricao", rendaFixaRequestDTO.getDescricao());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(RENDAFIXA_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString());

        mockMvc.perform( request )
                .andExpect( MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize(5)));


        BDDMockito.verify( rendaFixaService, BDDMockito.never()).salvar(Mockito.any(RendaFixa.class));
    }

    @Test
    @DisplayName("/API/RENDAFIXA - POST - Deve retornar not found por usuário não encontrado na base de dados")
    public void naoSalvarRendaFixaComUsusuarioNaoEncontrado() throws Exception {
        BigDecimal rentabilidadeDiaria = BigDecimal.valueOf(0.02);
        BigDecimal rentabilidadeMensal = BigDecimal.valueOf(0.65);
        LocalDate vencimento = LocalDate.of(2025, 12, 31);
        BigDecimal preco = BigDecimal.valueOf(1000.00);

        RendaFixaRequestDTO rendaFixaRequestDTO = RendaFixaRequestDTO.builder()
                .descricao("Tesouro Direto")
                .preco(preco)
                .rentabilidadeDiaria(rentabilidadeDiaria)
                .rentabilidadeMensal(rentabilidadeMensal)
                .vencimento(vencimento)
                .usuario(1L)
                .build();


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("descricao", rendaFixaRequestDTO.getDescricao());
        jsonObject.put("rentabilidadeDiaria", rendaFixaRequestDTO.getRentabilidadeDiaria());
        jsonObject.put("vencimento", "01/01/2025");
        jsonObject.put("preco", rendaFixaRequestDTO.getPreco());
        jsonObject.put("rentabilidadeMensal", rendaFixaRequestDTO.getRentabilidadeMensal());
        jsonObject.put("usuario", rendaFixaRequestDTO.getUsuario());


        BDDMockito.given( usuarioService.getUsuarioById(Mockito.anyLong())).willThrow( new UsuarioNotFoundException());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(RENDAFIXA_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString());

        mockMvc.perform( request )
                .andExpect( MockMvcResultMatchers.status().isNotFound())
                .andExpect( MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize(1)))
                .andExpect( MockMvcResultMatchers.jsonPath("errors[0]", Matchers.containsString("Usuário não encontrado na base de dados")));

        BDDMockito.verify( rendaFixaService, BDDMockito.never()).salvar(BDDMockito.any());

    }

    @Test
    @DisplayName("/API/RENDAFIXA - PUT - Deve atualizar um ativo")
    public void atualizarRendaFixa() throws Exception{
        BigDecimal rentabilidadeDiaria = BigDecimal.valueOf(0.02);
        BigDecimal rentabilidadeMensal = BigDecimal.valueOf(0.65);
        LocalDate vencimento = LocalDate.of(2025, 12, 31);
        BigDecimal preco = BigDecimal.valueOf(1000.00);

        RendaFixaRequestDTO rendaFixaRequestDTO = RendaFixaRequestDTO.builder()
                .descricao("Tesouro Direto 2025")
                .preco(preco)
                .rentabilidadeDiaria(rentabilidadeDiaria)
                .rentabilidadeMensal(rentabilidadeMensal)
                .vencimento(vencimento)
                .usuario(1L)
                .build();

        Usuario usuarioMock = UsuarioControllerTest.newUsuario();
        usuarioMock.setIdUsuario(1L);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("descricao", rendaFixaRequestDTO.getDescricao());
        jsonObject.put("rentabilidadeDiaria", rendaFixaRequestDTO.getRentabilidadeDiaria());
        jsonObject.put("vencimento", "01/01/2025");
        jsonObject.put("preco", rendaFixaRequestDTO.getPreco());
        jsonObject.put("rentabilidadeMensal", rendaFixaRequestDTO.getRentabilidadeMensal());
        jsonObject.put("usuario", rendaFixaRequestDTO.getUsuario());


        RendaFixa rendaFixaMock = new RendaFixa(1L, "Tesouro Direto 2025", LocalDateTime.now(), null,
                TipoAtivo.RENDAFIXA, usuarioMock, rentabilidadeDiaria,  vencimento, preco, rentabilidadeMensal );


        BDDMockito.given( rendaFixaService.atualizar(Mockito.any(RendaFixa.class))).willReturn(rendaFixaMock);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(RENDAFIXA_API.concat("/" + 1L ))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString());

        mockMvc.perform( request )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect( MockMvcResultMatchers.jsonPath("descricao").value(rendaFixaMock.getDescricao()));

    }
    @Test
    @DisplayName("/API/RENDAFIXA - PUT - Deve retornar NOT FOUND por ativo inexistente")
    public void naoAtualizarRendaFixaTest() throws Exception{

        BigDecimal rentabilidadeDiaria = BigDecimal.valueOf(0.02);
        BigDecimal rentabilidadeMensal = BigDecimal.valueOf(0.65);
        LocalDate vencimento = LocalDate.of(2025, 12, 31);
        BigDecimal preco = BigDecimal.valueOf(1000.00);

        RendaFixaRequestDTO rendaFixaRequestDTO = RendaFixaRequestDTO.builder()
                .descricao("Tesouro Direto")
                .preco(preco)
                .rentabilidadeDiaria(rentabilidadeDiaria)
                .rentabilidadeMensal(rentabilidadeMensal)
                .vencimento(vencimento)
                .usuario(1L)
                .build();



        JSONObject jsonObject = new JSONObject();
        jsonObject.put("descricao", rendaFixaRequestDTO.getDescricao());
        jsonObject.put("rentabilidadeDiaria", rendaFixaRequestDTO.getRentabilidadeDiaria());
        jsonObject.put("vencimento", "01/01/2025");
        jsonObject.put("preco", rendaFixaRequestDTO.getPreco());
        jsonObject.put("rentabilidadeMensal", rendaFixaRequestDTO.getRentabilidadeMensal());
        jsonObject.put("usuario", rendaFixaRequestDTO.getUsuario());



        BDDMockito.given( rendaFixaService.atualizar(Mockito.any())).willThrow(new RendaFixaNotFound());


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(RENDAFIXA_API.concat("/" + 1L ))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString());

        mockMvc.perform( request )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("/API/RENDAFIXA - DELETE - Deve retornar excluir um ativo de renda fixa")
    public void excluirRendaFixa() throws Exception{



        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(RENDAFIXA_API.concat("/" + 1L))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform( request )
                .andExpect( MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("/API/RENDAFIXA - DELETE - Deve retornar not found ao tentar exlcuir renda fixa inexiste")
    public void excluirRendaFixaInexistente() throws Exception{

        BDDMockito.doThrow( new RendaFixaNotFound()).when(rendaFixaService).excluir( Mockito.any());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(RENDAFIXA_API.concat("/" + 1L))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform( request )
                .andExpect( MockMvcResultMatchers.status().isNotFound())
                .andExpect( MockMvcResultMatchers.jsonPath("errors[0]").value( RendaFixaNotFound.MSG));


    }

    @Test
    @DisplayName("/API/RENDAFIXA - GET - Deve pesquisa ativo de renda")
    public void pesquisarRendaFixa() throws Exception{

        Usuario usuario = UsuarioServiceTest.createNewUsuario();
        usuario.setIdUsuario(1L);

        RendaFixa rendaFixaMock = new RendaFixa(1L, "Tesouro Selic teste",
                LocalDateTime.now(), LocalDateTime.now(), TipoAtivo.RENDAFIXA,
                usuario, BigDecimal.valueOf(0.02), LocalDate.of(2025, 1, 12), BigDecimal.valueOf(500.00),  BigDecimal.valueOf(0.50));

        BDDMockito.given( rendaFixaService.pesquisar(Mockito.any(RendaFixa.class), Mockito.any(Pageable.class)))
                .willReturn( new PageImpl<>(Collections.singletonList(rendaFixaMock), PageRequest.of(0, 100), 1));

        String descricao = "Selic";

        String queryString = String.format("?descricao=%s&page=0&size=100", descricao);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(RENDAFIXA_API.concat("/" + 1L + queryString)).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform( request ).andExpect( MockMvcResultMatchers.status().isOk());

    }

    @Test
    @DisplayName("/API/RENDAFIXA - GET - Deve retornar not found por usuário inexistente")
    public void pesquisarUsuarioInexistente() throws Exception{

        BDDMockito.given( usuarioService.getUsuarioById(Mockito.anyLong())).willThrow( new UsuarioNotFoundException());


        String descricao = "Selic";

        String queryString = String.format("?descricao=%s&page=0&size=100", descricao);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(RENDAFIXA_API.concat("/" + 1L + queryString)).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform( request ).andExpect( MockMvcResultMatchers.status().isNotFound())
                .andExpect( MockMvcResultMatchers.jsonPath("errors[0]", Matchers.containsStringIgnoringCase("Usuário não encontrado")));

    }



}
