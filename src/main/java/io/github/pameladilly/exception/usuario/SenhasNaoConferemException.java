package io.github.pameladilly.exception.usuario;

public class SenhasNaoConferemException extends RuntimeException {

    public SenhasNaoConferemException(){
        super("Senhas não conferem.");
    }
}
