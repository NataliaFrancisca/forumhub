package br.com.nat.forumhub.domain.topico.validacoes;

import br.com.nat.forumhub.domain.topico.Topico;
import br.com.nat.forumhub.domain.usuario.Usuario;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ValidadorUsuarioDonoTopico {

    public void valida(Topico topico, Usuario usuario){
        if (!Objects.equals(topico.getAutor().getId(), usuario.getId())){
            throw new AccessDeniedException("Somente o usuário que criou o tópico pode realizar atualizações.");
        }
    }

}
