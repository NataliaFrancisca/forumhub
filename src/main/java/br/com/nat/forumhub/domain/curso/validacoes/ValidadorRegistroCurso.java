package br.com.nat.forumhub.domain.curso.validacoes;

import br.com.nat.forumhub.domain.curso.CursoRegistro;
import br.com.nat.forumhub.domain.curso.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class ValidadorRegistroCurso {

    @Autowired
    private CursoRepository cursoRepository;

    public void validar(CursoRegistro curso){
        if (cursoRepository.existsByNome(curso.nome())){
            throw new DataIntegrityViolationException("Já existe um curso com esse título.");
        }
    }
}
