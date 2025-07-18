package br.com.nat.forumhub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicoRegistro(
        @NotBlank(message = "O campo título é obrigatório.")
        String titulo,
        @NotBlank(message = "O campo mensagem é obrigatório.")
        String mensagem,
        @NotNull(message = "O id do curso é obrigatório.")
        Long cursoId
) {
}
