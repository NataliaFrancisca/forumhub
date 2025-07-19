package br.com.nat.forumhub.domain.resposta;

import jakarta.validation.constraints.NotNull;

public record RespostaAtualizada(
        @NotNull
        Long respostaId,
        String mensagem,
        String solucao
) {
}
