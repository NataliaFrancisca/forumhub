package br.com.nat.forumhub.domain.resposta.validacoes;

import br.com.nat.forumhub.domain.resposta.Resposta;
import br.com.nat.forumhub.domain.usuario.Usuario;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ValidadorUsuarioDonoResposta {
    public void validar(Resposta resposta, Usuario usuario){
        if (!Objects.equals(resposta.getAutor().getId(), usuario.getId())){
            throw new AccessDeniedException("Somente o usuário que criou a resposta pode realizar atualizações.");
        }
    }
}
