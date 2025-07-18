package br.com.nat.forumhub.domain.topico.validacoes;

import br.com.nat.forumhub.domain.topico.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class ValidadorTopicoDuplicado {

    @Autowired
    private TopicoRepository topicoRepository;

    public void valida(String titulo, String mensagem){
        var topicoJaExiste = this.topicoRepository.existsByTituloAndMensagem(titulo, mensagem);

        if (topicoJaExiste){
            throw new DataIntegrityViolationException("Tópico já foi registrado.");
        }
    }
}
