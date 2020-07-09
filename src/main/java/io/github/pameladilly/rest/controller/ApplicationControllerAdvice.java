package io.github.pameladilly.rest.controller;

import io.github.pameladilly.exception.rendafixa.RendaFixaNotFound;
import io.github.pameladilly.exception.rendavariavel.RendaVariavelNotFound;
import io.github.pameladilly.exception.transacao.TransacaoNotFound;
import io.github.pameladilly.exception.usuario.SenhaInvalidaException;
import io.github.pameladilly.exception.usuario.SenhasNaoConferemException;
import io.github.pameladilly.exception.usuario.UsuarioNotFoundException;
import io.github.pameladilly.rest.ApiErrors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(SenhasNaoConferemException.class)
    @ResponseStatus( HttpStatus.BAD_REQUEST)
    public ApiErrors handleSenhasNaoConferemException(SenhasNaoConferemException ex) {
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return new ApiErrors(errors);
    }


    @ExceptionHandler(UsuarioNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleUsuarioNotFoundException(UsuarioNotFoundException ex) {
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler({SenhaInvalidaException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrors handleSenhaInvalidaException(SenhaInvalidaException ex) {
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(RendaFixaNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleRendaFixaNotFound(RendaFixaNotFound ex) {
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(RendaVariavelNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleRendaVariavelNotFound(RendaVariavelNotFound ex) {
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(TransacaoNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleTransacaoNotFound(TransacaoNotFound ex) {
        return new ApiErrors(ex.getMessage());
    }

}
