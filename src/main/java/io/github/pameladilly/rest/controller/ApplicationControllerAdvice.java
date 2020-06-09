package io.github.pameladilly.rest.controller;

import io.github.pameladilly.exception.usuario.SenhaInvalidaException;
import io.github.pameladilly.exception.usuario.SenhasNaoConferemException;
import io.github.pameladilly.exception.usuario.UsuarioNotFoundException;
import io.github.pameladilly.rest.ApiErrors;
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
                .map( e -> e.getDefaultMessage())
                .collect(Collectors.toList());

        return new ApiErrors(errors);
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleUsuarioNotFounException(UsuarioNotFoundException ex) {
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(SenhaInvalidaException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrors handleSenhaInvalidaException(SenhaInvalidaException ex) {
        return new ApiErrors(ex.getMessage());
    }
}
