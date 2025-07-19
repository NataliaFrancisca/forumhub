package br.com.nat.forumhub.domain.topico;

import br.com.nat.forumhub.domain.resposta.RespostaDadosDetalhados;

import java.time.LocalDateTime;
import java.util.List;

public record TopicoDadosDetalhadosComRespostas(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime data,
        Status status,
        String autor,
        String curso,
        List<RespostaDadosDetalhados> respostas
) {
    public TopicoDadosDetalhadosComRespostas(Topico topico){
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getAutor().getNome(),
                topico.getCurso().getNome(),
                topico.getRespostas().stream().map(RespostaDadosDetalhados::new).toList()
        );
    }
}
