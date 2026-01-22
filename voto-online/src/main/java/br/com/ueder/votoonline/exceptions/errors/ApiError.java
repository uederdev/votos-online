package br.com.ueder.votoonline.exceptions.errors;

import java.time.LocalDateTime;

public class ApiError {

    private String mensagem;
    private final LocalDateTime data = LocalDateTime.now();

    public ApiError(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
