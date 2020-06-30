package io.github.pameladilly.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.pameladilly.domain.entity.RendaFixa;
import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.domain.enums.TipoAtivo;
import io.github.pameladilly.rest.dto.RendaFixaRequestDTO;
import io.github.pameladilly.service.RendaFixaService;
import io.github.pameladilly.service.UsuarioService;
import org.json.JSONObject;
import org.json.JSONString;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import javax.print.attribute.standard.Media;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @DisplayName("Deve salvar um ativo de renda fixa")
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


        RendaFixa rendaFixaMock = new RendaFixa(1L, "Tesouro Direto", LocalDateTime.now(),
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
}
