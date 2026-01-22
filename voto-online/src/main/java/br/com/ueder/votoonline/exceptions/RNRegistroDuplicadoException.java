package br.com.ueder.votoonline.exceptions;

public class RNRegistroDuplicadoException extends RuntimeException{

    public RNRegistroDuplicadoException(String message) {
        super("Registro já existe na base de dados: " + message);
    }
}
