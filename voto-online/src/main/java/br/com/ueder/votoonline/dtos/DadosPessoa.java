package br.com.ueder.votoonline.dtos;

import java.time.LocalDate;

public record DadosPessoa(
        String controle,
        Long id,
        String nomeCompleto,
        LocalDate dataAdmissao,
        LocalDate dataRescisao,
        String matricula,
        DadosCargo cargo,
        DadosSetor setor,
        String pathFoto
) {
}
