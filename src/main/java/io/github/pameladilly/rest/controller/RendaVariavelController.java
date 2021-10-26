package io.github.pameladilly.rest.controller;

import io.github.pameladilly.domain.entity.RendaVariavel;
import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.domain.enums.TipoAtivo;
import io.github.pameladilly.rest.dto.CotacaoRequestDTO;
import io.github.pameladilly.rest.dto.RendaVariavelRequestDTO;
import io.github.pameladilly.rest.dto.RendaVariavelResponseDTO;
import io.github.pameladilly.service.RendaVariavelService;
import io.github.pameladilly.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    public RendaVariavelResponseDTO salvar(@RequestBody @Valid RendaVariavelRequestDTO rendaVariavelRequestDTO){

        RendaVariavel rendaVariavel = rendaVariavelRequestDTOToRendaVariavel(rendaVariavelRequestDTO);

        rendaVariavel = service.salvar(rendaVariavel);

        return  modelMapper.map(rendaVariavel, RendaVariavelResponseDTO.class);


    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void excluir( @PathVariable  Long id) {

        service.excluir(id);

    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public RendaVariavelResponseDTO atualizarCotacao(@PathVariable Long id, @RequestBody @Valid CotacaoRequestDTO cotacaoRequestDTO){

        RendaVariavel rendaVariavel = service.atualizarCotacao(id, cotacaoRequestDTO.getCotacao() );

        return rendaVariavelTORendaVariavelResponseDTO(rendaVariavel);

    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public RendaVariavelResponseDTO atualizar(@RequestBody @Valid RendaVariavelRequestDTO rendaVariavelRequestDTO) {

        RendaVariavel rendaVariavel = rendaVariavelRequestDTOToRendaVariavel(rendaVariavelRequestDTO);

        rendaVariavel = service.atualizar(rendaVariavel);

        return  rendaVariavelTORendaVariavelResponseDTO(rendaVariavel);

    }

    @GetMapping("{usuario}")
    @ResponseStatus(HttpStatus.OK)
    public Page<RendaVariavelResponseDTO> pesquisar(@PathVariable(name = "usuario") Long usuario,  RendaVariavelRequestDTO rendaVariavelRequestDTO, Pageable pageRequest){

        rendaVariavelRequestDTO.setUsuario(usuario);

        RendaVariavel filter = rendaVariavelRequestDTOToRendaVariavel(rendaVariavelRequestDTO);

        Page<RendaVariavel> result = service.pesquisar(filter, pageRequest);

        List<RendaVariavelResponseDTO> list = result.getContent().stream().map(
                this::rendaVariavelTORendaVariavelResponseDTO
        ).collect(Collectors.toList());


        return new PageImpl<RendaVariavelResponseDTO>(list, pageRequest, result.getTotalElements() );
    }

    private RendaVariavel rendaVariavelRequestDTOToRendaVariavel(RendaVariavelRequestDTO rendaVariavelRequestDTO){

        Usuario usuario = usuarioService.getUsuarioById(rendaVariavelRequestDTO.getUsuario());

        return new RendaVariavel(null, rendaVariavelRequestDTO.getDescricao(), null, null, TipoAtivo.RENDAVARIAVEL,
                usuario, rendaVariavelRequestDTO.getTicker(), rendaVariavelRequestDTO.getCotacao());
    }

    private RendaVariavelResponseDTO rendaVariavelTORendaVariavelResponseDTO(RendaVariavel rendaVariavel) {

        return  RendaVariavelResponseDTO.builder()
                .cotacao(rendaVariavel.getCotacao())
                .dataAtualizacao(rendaVariavel.getDataAtualizacao())
                .dataCadastro(rendaVariavel.getDataCadastro())
                .id(rendaVariavel.getIdAtivo())
                .ticker(rendaVariavel.getTicker())
                .usuario(rendaVariavel.getUsuario().getIdUsuario())
                .descricao(rendaVariavel.getDescricao())
                .build();

    }
}
