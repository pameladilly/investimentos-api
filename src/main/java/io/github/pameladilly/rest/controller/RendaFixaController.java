package io.github.pameladilly.rest.controller;

import io.github.pameladilly.domain.entity.RendaFixa;
import io.github.pameladilly.domain.entity.Usuario;
import io.github.pameladilly.domain.enums.TipoAtivo;
import io.github.pameladilly.rest.dto.RendaFixaRequestDTO;
import io.github.pameladilly.rest.dto.RendaFixaResponseDTO;
import io.github.pameladilly.service.RendaFixaService;
import io.github.pameladilly.service.UsuarioService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/rendafixa")
@RequiredArgsConstructor
public class RendaFixaController {

    private final RendaFixaService service;
    private final ModelMapper modelMapper;
    private final UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)

    public RendaFixaResponseDTO salvar( @RequestBody @Valid RendaFixaRequestDTO rendaFixaRequestDTO){


        RendaFixa rendaFixa = rendaFixaRequestDTOToRendaFixa(rendaFixaRequestDTO);

        rendaFixa = service.salvar(rendaFixa);

        return rendaFixaToRendaFixaResponseDTO(rendaFixa);
    }

    private RendaFixa rendaFixaRequestDTOToRendaFixa(RendaFixaRequestDTO rendaFixaRequestDTO){

        Usuario usuario = usuarioService.getUsuarioById(rendaFixaRequestDTO.getUsuario());


        return new RendaFixa(null, rendaFixaRequestDTO.getDescricao(), null, null, TipoAtivo.RENDAFIXA,
                usuario, rendaFixaRequestDTO.getRentabilidadeDiaria(),
                rendaFixaRequestDTO.getVencimento(),
                rendaFixaRequestDTO.getPreco(),
                rendaFixaRequestDTO.getRentabilidadeMensal());
    }

    private RendaFixaResponseDTO rendaFixaToRendaFixaResponseDTO(RendaFixa rendaFixa){

        return RendaFixaResponseDTO
                .builder()
                .dataCadastro(rendaFixa.getDataCadastro())
                .preco(rendaFixa.getPreco())
                .rentabilidadeDiaria(rendaFixa.getRentabilidadeDiaria())
                .rentabilidadeMensal(rendaFixa.getRentabilidadeMensal())
                .vencimento(rendaFixa.getVencimento())
                .descricao(rendaFixa.getDescricao())
                .tipoAtivo(TipoAtivo.RENDAFIXA.toString())
                .id(rendaFixa.getIdAtivo())
                .usuario(rendaFixa.getUsuario().getIdUsuario())
                .build();

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void excluir(@PathVariable Long id){

        RendaFixa rendaFixa = service.findById(id);

        service.excluir(rendaFixa);



    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public RendaFixaResponseDTO atualizar(@PathVariable Long id, @RequestBody @Valid RendaFixaRequestDTO rendaFixaRequestDTO){
        RendaFixa rendaFixa = rendaFixaRequestDTOToRendaFixa(rendaFixaRequestDTO);

        rendaFixa.setIdAtivo(id);

        rendaFixa = service.atualizar(rendaFixa);

        return rendaFixaToRendaFixaResponseDTO(rendaFixa);

    }

    @GetMapping(value = "{usuario}")
    @ResponseStatus(HttpStatus.OK)
    public Page<RendaFixaResponseDTO> pesquisar(@PathVariable(name = "usuario") Long usuario, RendaFixaRequestDTO rendaFixaRequestDTO, Pageable pageRequest){

        rendaFixaRequestDTO.setUsuario(usuario);

        RendaFixa filter = rendaFixaRequestDTOToRendaFixa(rendaFixaRequestDTO);

        Page<RendaFixa> result = service.pesquisar(filter, pageRequest);

        List<RendaFixaResponseDTO> list = result.getContent().stream().map(
                entity -> {
                    return modelMapper.map(entity, RendaFixaResponseDTO.class);
                }).collect(Collectors.toList());

        return new PageImpl<RendaFixaResponseDTO>( list, pageRequest, result.getTotalElements());
    }
}
