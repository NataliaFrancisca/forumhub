package br.com.nat.forumhub.domain.curso.validacoes;

import br.com.nat.forumhub.domain.usuario.Perfil;
import br.com.nat.forumhub.domain.usuario.Usuario;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class ValidadorRegistroFeitoPorProfessor {

    public void validar(Usuario usuario){
        boolean possuiPerfilProfessor = usuario.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals("ROLE_" + Perfil.PROFESSOR.name()));

        if (!possuiPerfilProfessor){
            throw new AccessDeniedException("Somente usuários com perfil PROFESSOR podem realizar ações para curso.");
        }
    }
}

