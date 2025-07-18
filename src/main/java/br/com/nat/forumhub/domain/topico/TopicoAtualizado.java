package br.com.nat.forumhub.domain.topico;

import jakarta.validation.constraints.NotNull;

public record TopicoAtualizado(
        @NotNull
        Long id,
        String titulo,
        String mensagem
) {
}
