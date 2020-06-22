package io.github.pameladilly.exception.carteira;

import org.omg.SendingContext.RunTime;

public class CarteiraNotFound extends RuntimeException {

    public CarteiraNotFound() {
        super("Carteira não encontrada na base de dados.");
    }
}
