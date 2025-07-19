package br.com.nat.forumhub.domain.resposta;

import br.com.nat.forumhub.domain.resposta.validacoes.ValidadorRespostaDuplicada;
import br.com.nat.forumhub.domain.resposta.validacoes.ValidadorUsuarioDonoResposta;
import br.com.nat.forumhub.domain.resposta.validacoes.ValidadorUsuarioPodeRealizarAcao;
import br.com.nat.forumhub.domain.topico.TopicoRepository;
import br.com.nat.forumhub.domain.usuario.Usuario;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RespostaService {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private ValidadorUsuarioPodeRealizarAcao validadorUsuarioPodeRealizarAcao;

    @Autowired
    private ValidadorRespostaDuplicada validadorRespostaDuplicada;

    @Autowired
    private ValidadorUsuarioDonoResposta validadorUsuarioDonoResposta;


    private Resposta buscarResposta(Long respostaId){
        return this.respostaRepository.findById(respostaId)
                .orElseThrow(() -> new EntityNotFoundException("Não existe resposta com esse número de ID."));
    }

    public RespostaDadosDetalhadosComTopico registrar(RespostaRegistro respostaRegistro, Usuario usuario){
        var topico = topicoRepository.findById(respostaRegistro.topicoId())
                .orElseThrow(() -> new EntityNotFoundException("Não existe tópico ocm esse número de ID."));

        this.validadorRespostaDuplicada.validar(respostaRegistro.mensagem(), respostaRegistro.solucao());

        Resposta resposta = new Resposta(respostaRegistro);
        resposta.setTopico(topico);
        resposta.setAutor(usuario);

        return new RespostaDadosDetalhadosComTopico(this.respostaRepository.save(resposta));
    }

    public RespostaDadosDetalhadosComTopico buscar(Long respostaId){
        var resposta = this.buscarResposta(respostaId);
        return new RespostaDadosDetalhadosComTopico(resposta);
    }

    public Page<RespostaDadosDetalhados> buscarTodas(Pageable paginacao, Long topicoId){
        return this.respostaRepository.findAllByTopicoId(paginacao, topicoId)
                .map(RespostaDadosDetalhados::new);
    }

    public RespostaDadosDetalhadosComTopico editar(RespostaAtualizada respostaAtualizada, Usuario usuario){
        var resposta = this.buscarResposta(respostaAtualizada.respostaId());

        this.validadorUsuarioDonoResposta.validar(resposta, usuario);

        var mensagem = Optional.ofNullable(respostaAtualizada.mensagem()).orElse(resposta.getMensagem());
        var solucao = Optional.ofNullable(respostaAtualizada.solucao()).orElse(resposta.getSolucao());

        this.validadorRespostaDuplicada.validar(mensagem, solucao);

        resposta.atualizar(respostaAtualizada);
        return new RespostaDadosDetalhadosComTopico(resposta);
    }

    public void deletar(Long respostaId, Usuario usuario){
        var resposta = this.buscarResposta(respostaId);

        this.validadorUsuarioPodeRealizarAcao.validar(resposta, usuario);
        this.respostaRepository.delete(resposta);
    }
}
