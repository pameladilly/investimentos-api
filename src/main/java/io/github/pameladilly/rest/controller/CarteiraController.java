package io.github.pameladilly.rest.controller;

import io.github.pameladilly.domain.entity.Carteira;
import io.github.pameladilly.rest.dto.CarteiraRequestDTO;
import io.github.pameladilly.rest.dto.CarteiraResponseDTO;
import io.github.pameladilly.rest.dto.UsuarioResponseDTO;
import io.github.pameladilly.service.CarteiraService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/carteira")
public class CarteiraController {

    private final CarteiraService service;
    private final ModelMapper modelMapper;

    public CarteiraController(CarteiraService service) {
        this.service = service;
        this.modelMapper = new ModelMapper();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarteiraResponseDTO salvar(@RequestBody CarteiraRequestDTO carteiraRequestDTO){

        Carteira carteira = modelMapper.map(carteiraRequestDTO, Carteira.class);
        carteira.setIdCarteira(null);
        carteira = service.salvar(carteira);

        UsuarioResponseDTO usuarioRespDTO = modelMapper.map( carteira.getUsuario(), UsuarioResponseDTO.class );
        CarteiraResponseDTO carteiraRespDTO = modelMapper.map( service.salvar(carteira), CarteiraResponseDTO.class );
        carteiraRespDTO.setUsuarioResponseDTO(usuarioRespDTO);

        return carteiraRespDTO;

    }

}
