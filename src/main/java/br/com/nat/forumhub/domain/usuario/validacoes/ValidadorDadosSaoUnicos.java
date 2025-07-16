package br.com.nat.forumhub.domain.usuario.validacoes;

import br.com.nat.forumhub.domain.usuario.UsuarioRegistro;
import br.com.nat.forumhub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class ValidadorDadosSaoUnicos{

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void validar(UsuarioRegistro dados) {
        var email = usuarioRepository.existsByEmail(dados.email());

        if (email){
            throw new DataIntegrityViolationException("E-mail indicado já está em uso.");
        }
    }
}
