package br.com.nat.forumhub.domain.topico;

import br.com.nat.forumhub.domain.curso.CursoRepository;
import br.com.nat.forumhub.domain.topico.validacoes.ValidadorTopicoDuplicado;
import br.com.nat.forumhub.domain.topico.validacoes.ValidadorUsuarioDonoTopico;
import br.com.nat.forumhub.domain.topico.validacoes.ValidadorUsuarioPodeRealizarAcao;
import br.com.nat.forumhub.domain.usuario.Usuario;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ValidadorTopicoDuplicado validadorTopicoDuplicado;

    @Autowired
    private ValidadorUsuarioDonoTopico validadorUsuarioDonoTopico;

    @Autowired
    private ValidadorUsuarioPodeRealizarAcao validadorUsuarioPodeRealizarAcao;

    private Topico buscarTopico(Long id){
        return this.topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não existe tópico com esse número de ID."));
    }

    public TopicoDadosDetalhados registrar(TopicoRegistro topicoRegistro, Usuario usuario){
        this.validadorTopicoDuplicado.valida(topicoRegistro.titulo(), topicoRegistro.mensagem());

        Topico topico = new Topico(topicoRegistro);

        var curso = this.cursoRepository
                .findById(topicoRegistro.cursoId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Não existe curso com esse número de ID.")
                );

        topico.setAutor(usuario);
        topico.setCurso(curso);
        return new TopicoDadosDetalhados(this.topicoRepository.save(topico));
    }

    public TopicoDadosDetalhadosComRespostas buscar(Long id){
        var topico = this.buscarTopico(id);
        return new TopicoDadosDetalhadosComRespostas(topico);
    }

    public Page<TopicoDadosDetalhadosComRespostas> buscarTodos(Pageable paginacao, Long cursoId){
        this.cursoRepository.findById(cursoId)
                .orElseThrow(() -> new IllegalArgumentException("Não existe curso com esse número de ID."));

        return this.topicoRepository.findAllByCursoId(paginacao, cursoId)
                .map(TopicoDadosDetalhadosComRespostas::new);
    }

    public TopicoDadosDetalhados editar(TopicoAtualizado topicoAtualizado, Usuario usuario){
        var topico = this.buscarTopico(topicoAtualizado.id());

        this.validadorUsuarioDonoTopico.valida(topico, usuario);

        var titulo = Optional.ofNullable(topicoAtualizado.titulo()).orElse(topico.getTitulo());
        var mensagem = Optional.ofNullable(topicoAtualizado.mensagem()).orElse(topico.getMensagem());

        this.validadorTopicoDuplicado.valida(titulo, mensagem);

        topico.atualizar(topicoAtualizado);
        return new TopicoDadosDetalhados(topico);
    }

    public TopicoDadosDetalhados atualizarStatus(TopicoStatusAtualizado topicoStatusAtualizado, Usuario usuario){
        var topico = this.buscarTopico(topicoStatusAtualizado.id());

        this.validadorUsuarioPodeRealizarAcao.validar(topico, usuario);
        topico.setStatus(topicoStatusAtualizado.status());

        return new TopicoDadosDetalhados(topico);
    }

    public void deletar(Long id, Usuario usuario){
        var topico = this.buscarTopico(id);
        this.validadorUsuarioPodeRealizarAcao.validar(topico, usuario);
        this.topicoRepository.deleteById(id);
    }
}
