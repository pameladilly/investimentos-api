package io.github.pameladilly.exception.rendavariavel;

public class RendaVariavelNotFound extends RuntimeException{

    public static final String MSG = "Renda Varíavel não econtrada na base de dados.";


    public RendaVariavelNotFound() {
        super(MSG);
    }
}
