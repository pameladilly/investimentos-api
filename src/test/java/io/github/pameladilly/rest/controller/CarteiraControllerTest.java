package io.github.pameladilly.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.pameladilly.domain.entity.Carteira;
import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.exception.usuario.UsuarioNotFoundException;
import io.github.pameladilly.rest.dto.CarteiraRequestDTO;
import io.github.pameladilly.service.CarteiraService;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WebMvcTest(controllers = CarteiraController.class)
public class CarteiraControllerTest {

    final static String CARTEIRA_API = "/api/carteira";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CarteiraService carteiraService;

    @Test
    @DisplayName("/API/CARTEIRA - POST - Salvar carteira")
    public void salvarCarteiraTest() throws Exception{


        Usuario usuario = UsuarioControllerTest.getNewUsuario();
        Carteira carteira = Carteira.builder().descricao("Carteira Test").usuario(usuario).idCarteira(1L).build();


        BDDMockito.given( carteiraService.salvar(Mockito.any(Carteira.class))).willReturn(carteira);

        CarteiraRequestDTO carteiraRequestDTO = newCarteiraRequestDTO();
        carteiraRequestDTO.setIdUsuario(1L);

        String json = new ObjectMapper().writeValueAsString(carteiraRequestDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(CARTEIRA_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("usuario").value(1L));
    }

    @Test
    @DisplayName("/API/CARTEIRA - POST - Não deve salvar carteira")
    public void naoDeveSalvarCarteiraTest() throws Exception {



        CarteiraRequestDTO carteiraRequestDTO = newCarteiraRequestDTO();
        carteiraRequestDTO.setIdUsuario(null);

        String json = new ObjectMapper().writeValueAsString(carteiraRequestDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(CARTEIRA_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", Matchers.hasSize(1)))
                .andExpect(jsonPath("errors[0]", Matchers.containsString("Informe o Id do usuário")));

    }

    @Test
    @DisplayName("/API/CARTEIRA - POST - Não deve salvar carteira - Usuário inexistente na base de dados")
    public void naoDeveSalvarCarteiraUsuarioNaoExiste() throws Exception {


        Usuario usuario = UsuarioControllerTest.getNewUsuario();
        Carteira carteira = Carteira.builder().descricao("Carteira Test").usuario(usuario).idCarteira(1L).build();


        BDDMockito.given( carteiraService.salvar(Mockito.any(Carteira.class))).willThrow(new UsuarioNotFoundException());

        CarteiraRequestDTO carteiraRequestDTO = newCarteiraRequestDTO();
        carteiraRequestDTO.setIdUsuario(2L);

        String json = new ObjectMapper().writeValueAsString(carteiraRequestDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(CARTEIRA_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("errors", Matchers.hasSize(1)))
                .andExpect(jsonPath("errors[0]", Matchers.containsString ("Usuário não encontrado na base de dados")));


    }

    private CarteiraRequestDTO newCarteiraRequestDTO() {


        return CarteiraRequestDTO.builder().idUsuario(1L).descricao("Carteira Test").build();
    }


}
