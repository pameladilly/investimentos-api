package io.github.pameladilly.exception.rendafixa;

public class RendaFixaNotFound extends RuntimeException {

    public RendaFixaNotFound() {
        super("Renda Fixa não econtrada na base de dados.");
    }
}
