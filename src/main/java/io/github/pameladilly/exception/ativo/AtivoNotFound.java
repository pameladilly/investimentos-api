package io.github.pameladilly.exception.ativo;

public class AtivoNotFound extends RuntimeException{

    public static final String MSG = "Ativo não encontrado na base de dados";

    public AtivoNotFound() {
        super(MSG);
    }
}
