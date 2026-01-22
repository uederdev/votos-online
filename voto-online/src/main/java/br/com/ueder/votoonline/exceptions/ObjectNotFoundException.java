package br.com.ueder.votoonline.exceptions;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String message) {
        super("Registro não encontrado: " + message);
    }
}
