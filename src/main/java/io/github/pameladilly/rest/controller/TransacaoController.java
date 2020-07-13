package io.github.pameladilly.rest.controller;

import io.github.pameladilly.domain.entity.*;
import io.github.pameladilly.domain.enums.TipoTransacao;
import io.github.pameladilly.exception.ativo.AtivoNotFound;
import io.github.pameladilly.exception.carteira.CarteiraNotFound;
import io.github.pameladilly.exception.usuario.UsuarioNotFoundException;
import io.github.pameladilly.rest.dto.TransacaoRequestDTO;
import io.github.pameladilly.rest.dto.TransacaoResponseDTO;
import io.github.pameladilly.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.omg.CORBA.TRANSACTION_MODE;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/transacao")
@RequiredArgsConstructor
public class TransacaoController {

    private final ModelMapper modelMapper;
    private final TransacaoService service;
    private final UsuarioService usuarioService;
    private final AtivoService ativoService;
    private final CarteiraService carteiraService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransacaoResponseDTO salvar(@RequestBody @Valid TransacaoRequestDTO transacaoRequestDTO){

        Transacao transacao = transacaoRequestDTOToTransacao(transacaoRequestDTO);

        transacao = service.salvar(transacao);


        return transacaoToTransacaoResponseDTO(transacao);
    }

    private Transacao transacaoRequestDTOToTransacao(TransacaoRequestDTO transacaoRequestDTO){

        Ativo ativo = ativoService.findByIdAndUsuario(transacaoRequestDTO.getAtivo(), transacaoRequestDTO.getUsuario());


        Carteira carteira = carteiraService.findById(transacaoRequestDTO.getCarteira());


        return Transacao.builder()
                .ativo(ativo)
                .carteira(carteira)
                .tipoTransacao(TipoTransacao.valueOf(transacaoRequestDTO.getTipoTransacao()))
                .data(transacaoRequestDTO.getData())
                .quantidade(transacaoRequestDTO.getQuantidade())
                .valorUnitario(transacaoRequestDTO.getValorUnitario())
                .build();

    }

    private TransacaoResponseDTO transacaoToTransacaoResponseDTO(Transacao transacao){

        return TransacaoResponseDTO.builder()
                .id(transacao.getIdTransacao())
                .ativo(transacao.getAtivo().getIdAtivo())
                .carteira(transacao.getCarteira().getIdCarteira())
                .usuario(transacao.getAtivo().getUsuario().getIdUsuario())
                .dataAtualizacao(transacao.getDataAtualizacao())
                .dataCadastro(transacao.getDataCadastro())
                .valorUnitario(transacao.getValorUnitario())
                .quantidade(transacao.getQuantidade())
                .tipoTransacao(transacao.getTipoTransacao().toString())
                .total(transacao.getTotal())
                .build();
    }


}
