package io.github.pameladilly.rest.controller;

import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.rest.dto.CredenciaisDTO;
import io.github.pameladilly.rest.dto.UsuarioDTO;
import io.github.pameladilly.service.impl.UsuarioServiceImpl;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    public UsuarioController(UsuarioServiceImpl usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salvar um novo usuário")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuário cadastrado"),
            @ApiResponse(code = 400, message = "Dados inválidos")
    })
    public Usuario salvar(@RequestBody @Valid UsuarioDTO usuarioDTO) {

        return usuarioService.salvar(usuarioDTO);
    }

    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Autenticar usuário")
    @ApiResponses( {
            @ApiResponse(code = 200, message = "Usuário autenticado" ),
            @ApiResponse(code = 403, message = "Senha inválida"),
            @ApiResponse(code = 404, message = "Usuário não encontrado na base de dados"),


    })
    public Usuario autenticar(@RequestBody CredenciaisDTO credenciaisDTO) {

        return usuarioService.carregar(credenciaisDTO.getLogin(), credenciaisDTO.getSenha());

    }
}
