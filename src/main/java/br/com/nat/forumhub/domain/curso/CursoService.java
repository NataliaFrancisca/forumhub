package br.com.nat.forumhub.domain.curso;

import br.com.nat.forumhub.domain.curso.validacoes.ValidadorRegistroCurso;
import br.com.nat.forumhub.domain.curso.validacoes.ValidadorRegistroFeitoPorProfessor;
import br.com.nat.forumhub.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ValidadorRegistroCurso validadorRegistroCurso;

    @Autowired
    private ValidadorRegistroFeitoPorProfessor validadorRegistroFeitoPorProfessor;

    public CursoDadosDetalhados registrar(CursoRegistro cursoRegistro, Usuario usuario){
        this.validadorRegistroFeitoPorProfessor.validar(usuario);
        this.validadorRegistroCurso.validar(cursoRegistro);

        Curso curso = new Curso(cursoRegistro);
        var cursoSalvo = this.cursoRepository.save(curso);

        return new CursoDadosDetalhados(cursoSalvo);
    }

    public CursoDadosDetalhados buscar(Long id){
        var curso = this.cursoRepository.getReferenceById(id);
        return new CursoDadosDetalhados(curso);
    }
}
