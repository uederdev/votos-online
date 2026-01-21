package br.com.ueder.votoonline.exceptions;

import java.time.LocalDateTime;

public class ApiError {

    private String mensagem;
    private LocalDateTime data = LocalDateTime.now();

    public ApiError(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
