package io.github.pameladilly.exception.transacao;

public class TransacaoNotFound extends RuntimeException {

    public static final String MSG = "Transação não encontrada na base de dados.";

    public TransacaoNotFound() {
        super(MSG);
    }
}
