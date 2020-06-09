package io.github.pameladilly.exception.usuario;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SenhaInvalidaException extends RuntimeException {

    public SenhaInvalidaException() {
        super("Senha inválida.");
    }
}
