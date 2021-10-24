package io.github.pameladilly.exception.usuario;

public class UsuarioNotFoundException extends RuntimeException {
    public static final String MSG = "Usuário não encontrado na base de dados.";
    public UsuarioNotFoundException() {
        super(MSG);
    }
}
