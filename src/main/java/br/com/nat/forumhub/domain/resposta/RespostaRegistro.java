package br.com.nat.forumhub.domain.resposta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RespostaRegistro(
        @NotBlank(message = "O campo mensagem é obrigatório.")
        String mensagem,
        @NotBlank(message = "O campo solução é obrigatório.")
        String solucao,
        @NotNull(message = "O campo de identificação do tópico é obrigatório.")
        Long topicoId
) {
}
