package br.com.nat.forumhub.domain.resposta.validacoes;

import br.com.nat.forumhub.domain.resposta.Resposta;
import br.com.nat.forumhub.domain.usuario.Perfil;
import br.com.nat.forumhub.domain.usuario.Usuario;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("validadorUsuarioPodeRealizarAcaoResposta")
public class ValidadorUsuarioPodeRealizarAcao {
    public void validar(Resposta resposta, Usuario usuario){
        boolean possuiPerfilProfessorOuModerador = usuario.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals("ROLE_" + Perfil.PROFESSOR.name()) || auth.equals("ROLE_" + Perfil.MODERADOR.name()));

        if (!possuiPerfilProfessorOuModerador){
            if (!Objects.equals(resposta.getAutor().getId(), usuario.getId())){
                throw new AccessDeniedException("Somente o dono da resposta, professor e moderador podem realizar ações.");
            }
        }
    }
}
