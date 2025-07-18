package br.com.nat.forumhub.domain.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CursoRegistro(
        @NotBlank(message = "O campo nome é obrigatório.")
        String nome,
        @NotNull(message = "A categoria do curso é obrigatório.")
        String categoria
) {
}
