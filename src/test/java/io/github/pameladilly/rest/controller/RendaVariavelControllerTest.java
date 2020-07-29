package io.github.pameladilly.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.pameladilly.domain.entity.RendaVariavel;
import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.domain.repository.RendaVariavelRepository;
import io.github.pameladilly.exception.usuario.UsuarioNotFoundException;
import io.github.pameladilly.rest.dto.RendaVariavelRequestDTO;
import io.github.pameladilly.service.RendaVariavelService;
import io.github.pameladilly.service.RendaVariavelServiceTest;
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
import java.util.Collections;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WebMvcTest(controllers = RendaVariavelController.class)
public class RendaVariavelControllerTest {

    static final String API_RENDAVARIAVEL = "/api/rendavariavel";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RendaVariavelRepository repository;

    @MockBean
    RendaVariavelService service;

    @MockBean
    UsuarioService usuarioService;



    @Test
    @DisplayName("/API/RENDAVARIAVEL - POST - Salvar um ativo de renda variável ")
    public void salvarRendaVariavel() throws Exception{

        Usuario usuario = UsuarioServiceTest.createNewUsuario();
        usuario.setIdUsuario(1L);

        RendaVariavel rendaVariavel = RendaVariavelServiceTest.createRendaVariavel(usuario);

        RendaVariavelRequestDTO rendaVariavelRequestDTO = createRendaVariavelRequestDTO();

        BDDMockito.given( service.salvar(Mockito.any(RendaVariavel.class))).willReturn( rendaVariavel );


        String json = new ObjectMapper().writeValueAsString(rendaVariavelRequestDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API_RENDAVARIAVEL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(json);

        mockMvc.perform( request ).andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    @DisplayName("/API/RENDAVARIAVEL - POST - Deve retornar bad request por campos não informados")
    public void salvarRendaVariavelCamposNaoInformados() throws Exception {
        Usuario usuario = UsuarioServiceTest.createNewUsuario();
        usuario.setIdUsuario(1L);

        RendaVariavel rendaVariavel = RendaVariavelServiceTest.createRendaVariavel(usuario);

        RendaVariavelRequestDTO rendaVariavelRequestDTO = createRendaVariavelRequestDTO();


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("descricao", rendaVariavelRequestDTO.getDescricao());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API_RENDAVARIAVEL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(jsonObject.toString());

        mockMvc.perform( request )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize(3)));

        BDDMockito.verify( service, BDDMockito.never()).salvar(Mockito.any(RendaVariavel.class));
    }

    @Test
    @DisplayName("/API/RENDAVARIAVEL - POST - Deve retornar not found por usuário não encontrado")
    public void salvarRendaVariavelSemUsuario() throws Exception{

        Usuario usuario = UsuarioServiceTest.createNewUsuario();
        usuario.setIdUsuario(1L);

        RendaVariavel rendaVariavel = RendaVariavelServiceTest.createRendaVariavel(usuario);

        RendaVariavelRequestDTO rendaVariavelRequestDTO = createRendaVariavelRequestDTO();


        BDDMockito.given(service.salvar(Mockito.any())).willThrow( new UsuarioNotFoundException());

        String json = new ObjectMapper().writeValueAsString(rendaVariavelRequestDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API_RENDAVARIAVEL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(json);

        mockMvc.perform( request )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("errors[0]", Matchers.containsStringIgnoringCase("Usuário não encontrado na base de dados")));

        BDDMockito.verify( repository, BDDMockito.never()).save(Mockito.any(RendaVariavel.class));

    }

    @Test
    @DisplayName("/API/RENDAVARIAVEL - GET - Pesquisar um ativo de renda variável")
    public void pesquisarTest() throws Exception{

        Usuario usuario = UsuarioServiceTest.createNewUsuario();
        usuario.setIdUsuario(1L);

        RendaVariavel rendaVariavelMock = RendaVariavelServiceTest.createRendaVariavel(usuario);
        rendaVariavelMock.setTicker("WEGE3");

        String ticker = "WEGE3";

        BDDMockito.given( service.pesquisar(Mockito.any(RendaVariavel.class), Mockito.any(Pageable.class)))
                .willReturn( new PageImpl<>(Collections.singletonList(rendaVariavelMock), PageRequest.of(0, 100), 1));

        String queryString = String.format("?ticker=%s&page=0&size=100", ticker);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API_RENDAVARIAVEL.concat("/" + 1L + queryString ))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform( request ).andExpect( MockMvcResultMatchers.status().isOk())
                .andExpect( MockMvcResultMatchers.jsonPath("content", Matchers.hasSize(1)))
                .andExpect( MockMvcResultMatchers.jsonPath( "totalElements").value(1))
                .andExpect( MockMvcResultMatchers.jsonPath( "pageable.pageSize").value(100))
                .andExpect( MockMvcResultMatchers.jsonPath( "pageable.pageNumber").value(0));

    }

    @Test
    @DisplayName("/API/RENDAVARIAVEL - GET - Deve retornar not found ao passar Id de usuário não cadastrado")
    public void pesquisarUsuarioInexistente() throws Exception {
        Usuario usuario = UsuarioServiceTest.createNewUsuario();
        usuario.setIdUsuario(1L);

        RendaVariavel rendaVariavelMock = RendaVariavelServiceTest.createRendaVariavel(usuario);
        rendaVariavelMock.setTicker("WEGE3");

        String ticker = "WEGE3";

        BDDMockito.given( usuarioService.getUsuarioById(Mockito.anyLong())).willThrow(new UsuarioNotFoundException());

        String queryString = String.format("?ticker=%s&page=0&size=100", ticker);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API_RENDAVARIAVEL.concat("/" + 1L + queryString ))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform( request).andExpect( MockMvcResultMatchers.status().isNotFound())
                .andExpect( MockMvcResultMatchers.jsonPath("errors[0]", Matchers.containsStringIgnoringCase("Usuário não encontrado")));
    }

    @Test
    @DisplayName("/API/RENDAVARIAVEL - PUT - Atualizar renda variável")
    public void atualizarRendaVariavel() throws Exception{

        Usuario usuario = UsuarioServiceTest.createNewUsuario();
        usuario.setIdUsuario(1L);

        RendaVariavel rendaVariavelMock = RendaVariavelServiceTest.createRendaVariavel(usuario);

        RendaVariavelRequestDTO rendaVariavelRequestDTO = createRendaVariavelRequestDTO();

        String json = new ObjectMapper().writeValueAsString(rendaVariavelRequestDTO);

        BDDMockito.given( service.atualizar(Mockito.any())).willReturn(rendaVariavelMock);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(API_RENDAVARIAVEL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform( request ).andExpect( MockMvcResultMatchers.status().isOk())
                .andExpect( MockMvcResultMatchers.jsonPath( "descricao").value(rendaVariavelMock.getDescricao()))
                .andExpect( MockMvcResultMatchers.jsonPath( "usuario").value(rendaVariavelMock.getUsuario().getIdUsuario()));

    }

    @Test
    @DisplayName("/API/RENDAVARIAVEL - PUT - Deve retornar not found por  usuário inexistente")
    public void atualizarRendaVariavelUsuarioInexistente() throws Exception{

        RendaVariavelRequestDTO rendaVariavelRequestDTO = createRendaVariavelRequestDTO();

        String json = new ObjectMapper().writeValueAsString(rendaVariavelRequestDTO);

        BDDMockito.given( usuarioService.getUsuarioById(Mockito.anyLong())).willThrow(new UsuarioNotFoundException());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(API_RENDAVARIAVEL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform( request ).andExpect( MockMvcResultMatchers.status().isNotFound())
                .andExpect( MockMvcResultMatchers.jsonPath("errors[0]", Matchers.containsStringIgnoringCase("Usuário não encontrado")));


    }

    @Test
    @DisplayName("/API/RENDAVARIAVEL - PATCH - Atualizar cotação")
    public void atualizarCotacao(){

    }

    @Test
    @DisplayName("/API/RENDAVARIAVEL - PATCH - Retornar not found ao atualizar cotação ativo inexistente")
    public void atualizarCotacaoAtivoInexisten(){

    }

    @Test
    @DisplayName("/API/RENDAVARIAVEL - PATCH - Retornar bad request por campos não informados")
    public void atualizarCotacaoCamposNaoInformados(){

    }


    @Test
    @DisplayName("/API/RENDAVARIAVEL - DELETE - Excluir renda variável")
    public void excluirRendaVariavel(){

    }

    @Test
    @DisplayName("/API/RENDAVARIAVEL - DELETE - Retornar not found para renda variável inexistente")
    public void excluirRendaVariavelInexistente(){

    }

    public RendaVariavelRequestDTO createRendaVariavelRequestDTO() {
        return RendaVariavelRequestDTO.builder().cotacao(BigDecimal.valueOf(56.90)).ticker("WEGE3").descricao("Weg S.A").usuario(1L).build();
    }


}
