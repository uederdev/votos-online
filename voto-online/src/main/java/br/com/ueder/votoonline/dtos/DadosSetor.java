package br.com.ueder.votoonline.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record DadosSetor(
        String controle,
        @NotNull
        Long id,
        @NotBlank
        @Length(min = 3, max = 80)
        String descricao) {
}
