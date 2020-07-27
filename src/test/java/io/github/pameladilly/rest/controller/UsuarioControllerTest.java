package io.github.pameladilly.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.deploy.net.CanceledDownloadException;
import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.exception.usuario.SenhasNaoConferemException;
import io.github.pameladilly.exception.usuario.UsuarioNotFoundException;
import io.github.pameladilly.rest.dto.CredenciaisRequestDTO;
import io.github.pameladilly.rest.dto.UsuarioRequestDTO;
import io.github.pameladilly.service.UsuarioService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AssertionsKt;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
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

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = UsuarioController.class)
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    static String USUARIO_API = "/api/usuarios";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UsuarioService service;

    @Test
    @DisplayName("/API/USUARIOS - POST - Salvar usuário")
    public void salvarUsuario() throws Exception {
        UsuarioRequestDTO usuarioRequestDTO = getNewUsuarioRequestDTO();

        Usuario savedUsuario = newUsuario();

        BDDMockito.given(service.salvar(Mockito.any(Usuario.class), Mockito.anyString())).willReturn(savedUsuario);

        String jsonRequest = new ObjectMapper().writeValueAsString(usuarioRequestDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                    .post(USUARIO_API)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(jsonRequest);


        mockMvc.perform(request)
                .andExpect( MockMvcResultMatchers.status().isCreated())
                .andExpect( MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect( MockMvcResultMatchers.jsonPath("nome").value(usuarioRequestDTO.getNome()))
                .andExpect( MockMvcResultMatchers.jsonPath("email").value(usuarioRequestDTO.getEmail()))
                .andExpect( MockMvcResultMatchers.jsonPath("login").value(usuarioRequestDTO.getLogin()));

    }

    static public Usuario newUsuario() {
        return Usuario.builder()
                                    .idUsuario(1L)
                                    .nome("Fulano de Assis")
                                    .email("usuario@usuaio.com")
                                    .senha("123")
                                    .dataCadastro(LocalDateTime.now())
                                    .login("usuario").build();
    }

    @Test
    @DisplayName("/API/USUARIOS - POST - Salvar usuário - Validar campos obrigatórios")
    public void salvarValidarCamposObrigatorios() throws Exception{
        UsuarioRequestDTO usuarioRequestDTO = getNewUsuarioRequestDTO();
        usuarioRequestDTO.setEmail(null);
        usuarioRequestDTO.setNome(null);


        String json = new ObjectMapper().writeValueAsString(usuarioRequestDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(USUARIO_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform( request )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize(2)));

    }
    @Test
    @DisplayName("/API/USUARIOS - POST - Salvar usuário - Validar senhas iguais")
    public void salvarValidarSenhasIguais() throws Exception{
        UsuarioRequestDTO usuarioRequestDTO = getNewUsuarioRequestDTO();
        usuarioRequestDTO.setSenha("123");
        usuarioRequestDTO.setSenhaConfirmacao("1234");

        String json = new ObjectMapper().writeValueAsString(usuarioRequestDTO);

        BDDMockito.given(service.salvar(Mockito.any(Usuario.class), Mockito.anyString())).willThrow(
               new SenhasNaoConferemException() );

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(USUARIO_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);


        mockMvc.perform( request )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath( "errors[0]").value("Senhas não conferem."));

    }

    @Test
    @DisplayName("/API/USUARIOS/AUTH - POST - Autenticar usuário")
    public void autenticar() throws Exception{

        CredenciaisRequestDTO credenciaisRequestDTO = CredenciaisRequestDTO.builder().login("user01").senha("123").build();
        Usuario usuarioAutenticacao = getNewUsuario();
        usuarioAutenticacao.setSenha("123");
        usuarioAutenticacao.setLogin("user01");

        BDDMockito.given(service.carregar(Mockito.anyString(), Mockito.anyString())).willReturn(usuarioAutenticacao);

        String json = new ObjectMapper().writeValueAsString(credenciaisRequestDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(USUARIO_API.concat("/auth"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value("Usuário Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value("usuario@test.com"));



    }

    @Test
    @DisplayName("/API/USUARIOS/{id} - DELETE - Excluir usuário inexistente" )
    public void excluirUsuarioInexiste() throws Exception {

        Long id = 2L;


        BDDMockito.doThrow(UsuarioNotFoundException.class).when(service).excluir(Mockito.anyLong());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(USUARIO_API.concat("/" + id));

        mockMvc.perform( request )
                .andExpect( MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize(1)));


    }

    @Test
    @DisplayName("/API/USUARIOS/{id} - DELETE - Excluir usuário inexistente")
    public void excluirUsuario() throws Exception{
        Long id = 1L;


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(USUARIO_API.concat("/" + id));

        mockMvc.perform( request )
                .andExpect( MockMvcResultMatchers.status().isOk());


    }
    @Test
    @DisplayName("/API/USUARIOS/{id} - PUT - Atualizar usuário")
    public void atualizar() throws Exception{
        UsuarioRequestDTO usuarioRequestDTO = getNewUsuarioRequestDTO();

        String nome = "Meu usuário";
        String email = "meusuario@gmail.com";
        Long id = 1L;

        Usuario usuarioSaved = getNewUsuario();
        usuarioSaved.setNome(nome);
        usuarioSaved.setEmail(email);
        usuarioSaved.setIdUsuario(id);
        BDDMockito.given(service.atualizar(Mockito.anyLong(), Mockito.any(), Mockito.anyString())).willReturn(usuarioSaved);

        usuarioRequestDTO.setNome(nome);
        usuarioRequestDTO.setEmail(email);

        String json = new ObjectMapper().writeValueAsString(usuarioRequestDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(USUARIO_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform( request )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.jsonPath("nome").value(usuarioRequestDTO.getNome()) )
                .andExpect( MockMvcResultMatchers.jsonPath( "email").value(usuarioRequestDTO.getEmail()));

    }

    @Test
    @DisplayName("/API/USUARIOS/{id} - GET - Retornar um usuário por id")
    public void getUsuarioPorId() throws Exception{

        String nome = "Meu usuário";
        String email = "meusuario@gmail.com";
        Long id = 1L;

        Usuario usuarioMock = getNewUsuario();
        usuarioMock.setNome(nome);
        usuarioMock.setEmail(email);
        usuarioMock.setIdUsuario(id);


        BDDMockito.given( service.getUsuarioById(Mockito.anyLong())).willReturn(usuarioMock);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(USUARIO_API.concat("/" + id));

        mockMvc.perform( request )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.jsonPath("id").value(usuarioMock.getIdUsuario()))
                .andExpect( MockMvcResultMatchers.jsonPath("email").value(usuarioMock.getEmail()));
    }

    @Test
    @DisplayName("/API/USUARIOS/{id} - GET - Retornar not found ao buscar usuário por id")
    public void getUsuarioPorIdInexistente() throws Exception{

        BDDMockito.given( service.getUsuarioById(Mockito.anyLong())).willThrow(new UsuarioNotFoundException());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(USUARIO_API.concat("/" + 1L));

        mockMvc.perform( request )
                .andExpect( MockMvcResultMatchers.status().isNotFound() );

    }

    public static  Usuario getNewUsuario() {
        return Usuario.builder().idUsuario(1L).email("usuario@test.com").nome("Usuário Test").login("usuario").senha("123").build();
    }

    private UsuarioRequestDTO getNewUsuarioRequestDTO() {
        return UsuarioRequestDTO.builder().nome("Fulano de Assis").email("usuario@usuaio.com").senha("123").senhaConfirmacao("123").login("usuario").build();
    }
}
