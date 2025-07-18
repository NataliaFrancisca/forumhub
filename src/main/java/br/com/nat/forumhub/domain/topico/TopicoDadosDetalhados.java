package br.com.nat.forumhub.domain.topico;

import java.time.LocalDateTime;

public record TopicoDadosDetalhados(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime data,
        Status status,
        String autor
) {
    public TopicoDadosDetalhados(Topico topico){
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getAutor().getNome()
        );
    }
}
