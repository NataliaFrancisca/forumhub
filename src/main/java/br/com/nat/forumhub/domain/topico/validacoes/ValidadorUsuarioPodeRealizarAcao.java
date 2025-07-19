package br.com.nat.forumhub.domain.topico.validacoes;

import br.com.nat.forumhub.domain.topico.Topico;
import br.com.nat.forumhub.domain.usuario.Perfil;
import br.com.nat.forumhub.domain.usuario.Usuario;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("validadorUsuarioPodeRealizarAcaoTopico")
public class ValidadorUsuarioPodeRealizarAcao {
    public void validar(Topico topico, Usuario usuario){
        boolean possuiPerfilProfessorOuModerador = usuario.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals("ROLE_" + Perfil.PROFESSOR.name()) || auth.equals("ROLE_" + Perfil.MODERADOR.name()));

        if (!possuiPerfilProfessorOuModerador){
            if (!Objects.equals(topico.getAutor().getId(), usuario.getId())){
                throw new AccessDeniedException("Somente o dono do tópico, professor e moderador podem atualizar status do tópico.");
            }
        }
    }
}
