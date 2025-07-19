package br.com.nat.forumhub.domain.resposta.validacoes;

import br.com.nat.forumhub.domain.resposta.RespostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class ValidadorRespostaDuplicada {
    @Autowired
    private RespostaRepository respostaRepository;

    public void validar(String mensagem, String solucao){
        var resposta =  this.respostaRepository.existsByMensagemAndSolucao(mensagem, solucao);

        if (resposta){
            throw new DataIntegrityViolationException("Essa resposta já existe para esse tópico.");
        }
    }
}
