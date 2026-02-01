package br.com.ueder.votoonline.dtos.pessoa;

import java.time.LocalDate;

public record DadosPessoa(
        String controle,
        Long id,
        String nomeCompleto,
        LocalDate dataAdmissao,
        LocalDate dataRescisao,
        String matricula,
        String pathFoto
) {
}
