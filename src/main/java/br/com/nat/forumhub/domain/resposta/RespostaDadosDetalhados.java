package br.com.nat.forumhub.domain.resposta;

import java.time.LocalDateTime;

public record RespostaDadosDetalhados(
        Long id,
        String mensagem,
        String solucao,
        String autor,
        LocalDateTime dataCriacao
) {
    public RespostaDadosDetalhados(Resposta resposta){
        this(
                resposta.id,
                resposta.getMensagem(),
                resposta.getSolucao(),
                resposta.getAutor().getNome(),
                resposta.dataCriacao
        );
    }
}
