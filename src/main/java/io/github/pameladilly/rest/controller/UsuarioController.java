package io.github.pameladilly.rest.controller;

import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.rest.dto.CredenciaisRequestDTO;
import io.github.pameladilly.rest.dto.UsuarioRequestDTO;
import io.github.pameladilly.rest.dto.UsuarioResponseDTO;
import io.github.pameladilly.service.UsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    final ModelMapper modelMapper;

    final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        this.modelMapper = new ModelMapper();
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Salvar um novo usuário")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuário cadastrado"),
            @ApiResponse(code = 400, message = "Dados inválidos")
    })
    public UsuarioResponseDTO salvar(@RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO) {

        Usuario usuario = modelMapper.map(usuarioRequestDTO, Usuario.class);

        String confirmacaoSenha = usuarioRequestDTO.getSenhaConfirmacao();


        usuario = usuarioService.salvar(usuario, confirmacaoSenha);


        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }

    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Autenticar usuário")
    @ApiResponses( {
            @ApiResponse(code = 200, message = "Usuário autenticado" ),
            @ApiResponse(code = 401, message = "Senha inválida"),
            @ApiResponse(code = 404, message = "Usuário não encontrado na base de dados"),


    })
    public UsuarioResponseDTO autenticar(@RequestBody CredenciaisRequestDTO credenciaisRequestDTO) {

        Usuario usuario = usuarioService
                            .carregar(credenciaisRequestDTO.getLogin(), credenciaisRequestDTO.getSenha());

        return modelMapper.map(usuario, UsuarioResponseDTO.class);

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Atualizar dados do usuário")
    @ApiResponses( {
            @ApiResponse(code = 200, message = "Dados atualizados."),
            @ApiResponse(code = 404, message = "Erro. Verificar mensagem de retorno.")

    })
    public UsuarioResponseDTO atualizar(@PathVariable Long id, @RequestBody UsuarioRequestDTO usuarioRequestDTO){

        Usuario usuarioRequest =  modelMapper.map(usuarioRequestDTO, Usuario.class);
        String confirmacaoSenha = usuarioRequestDTO.getSenhaConfirmacao();

        Usuario usuarioUpdate = usuarioService.atualizar(id, usuarioRequest, confirmacaoSenha);

        return modelMapper.map(usuarioUpdate, UsuarioResponseDTO.class);
    }



    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Excluir conta do usuário")
    @ApiResponses( {
            @ApiResponse(code = 204 , message = "Exclusão realizada" ),
            @ApiResponse(code = 404, message = "Erro. Verificar mensagem de retorno.")
    })
    public void excluir(@PathVariable Long id){

        usuarioService.excluir(id);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Consultar dados do usuário")
    @ApiResponses( {
            @ApiResponse(code = 200, message = "Consulta realizada"),
            @ApiResponse(code = 404, message = "Erro. Verificar mensagem de retorno.")

    })
    public UsuarioResponseDTO getUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioService.getUsuarioById(id);

        return modelMapper.map(usuario, UsuarioResponseDTO.class);

    }

}
