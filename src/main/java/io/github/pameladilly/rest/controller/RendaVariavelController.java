package io.github.pameladilly.rest.controller;

import io.github.pameladilly.domain.entity.RendaVariavel;
import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.domain.enums.TipoAtivo;
import io.github.pameladilly.rest.dto.RendaVariavelRequestDTO;
import io.github.pameladilly.rest.dto.RendaVariavelResponseDTO;
import io.github.pameladilly.service.RendaVariavelService;
import io.github.pameladilly.service.UsuarioService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/rendavariavel")
public class RendaVariavelController {

    private final RendaVariavelService service;
    private final UsuarioService usuarioService;
    private final ModelMapper modelMapper;

    public RendaVariavelController(RendaVariavelService service, UsuarioService usuarioService, ModelMapper modelMapper) {
        this.service = service;
        this.usuarioService = usuarioService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RendaVariavelResponseDTO salvar(RendaVariavelRequestDTO rendaVariavelRequestDTO){

        return null;
    }

    private RendaVariavel rendaVariavelRequestDTOToRendaVariavel(RendaVariavelRequestDTO rendaVariavelRequestDTO){

        Usuario usuario = usuarioService.getUsuarioById(rendaVariavelRequestDTO.getUsuario());

        return new RendaVariavel(null, rendaVariavelRequestDTO.getDescricao(), null, null, TipoAtivo.RENDAVARIAVEL,
                usuario, rendaVariavelRequestDTO.getTicker(), rendaVariavelRequestDTO.getCotacao());
    }
}
