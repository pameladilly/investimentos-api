package io.github.pameladilly.exception.carteira;

public class CarteiraNotFound extends RuntimeException {


    public static final String MSG = "Carteira não encontrada na base de dados.";

    public CarteiraNotFound() {
        super(MSG);
    }
}
