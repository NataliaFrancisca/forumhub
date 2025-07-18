package br.com.nat.forumhub.domain.topico;

import jakarta.validation.constraints.NotNull;

public record TopicoStatusAtualizado(
        @NotNull
        Long id,
        @NotNull
        Status status
) {
}
