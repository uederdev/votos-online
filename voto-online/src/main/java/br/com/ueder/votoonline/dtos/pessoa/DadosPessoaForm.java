package br.com.ueder.votoonline.dtos.pessoa;

import java.time.LocalDate;

public record DadosPessoaForm(
        String controle,
        Long id,
        String nomeCompleto,
        LocalDate dataAdmissao,
        LocalDate dataRescisao,
        String matricula,
        String cargo,
        String setor,
        String pathFoto
) {
}
