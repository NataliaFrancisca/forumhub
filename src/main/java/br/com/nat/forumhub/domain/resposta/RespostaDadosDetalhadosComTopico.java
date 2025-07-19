package br.com.nat.forumhub.domain.resposta;

import java.time.LocalDateTime;

public record RespostaDadosDetalhadosComTopico(
        Long id,
        String mensagem,
        String solucao,
        String autor,
        LocalDateTime dataCriacao,
        String topico
) {
    public RespostaDadosDetalhadosComTopico(Resposta resposta){
        this(
                resposta.getId(),
                resposta.getMensagem(),
                resposta.getSolucao(),
                resposta.getAutor().getNome(),
                resposta.getDataCriacao(),
                resposta.getTopico().getTitulo()
        );
    }
}
