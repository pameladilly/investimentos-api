package io.github.pameladilly.exception.usuario;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsuarioNotFoundException extends RuntimeException {

    public static final String MSG = "Usuário não encontrado na base de dados.";


    public UsuarioNotFoundException() {
        super(MSG);
    }
}
