package io.github.pameladilly.exception.rendafixa;

public class RendaFixaNotFound extends RuntimeException {

    public static final String MSG = "Renda Fixa não econtrada na base de dados.";

    public RendaFixaNotFound() {
        super(MSG);
    }
}
