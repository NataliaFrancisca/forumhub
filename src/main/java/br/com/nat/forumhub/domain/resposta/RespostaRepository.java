package br.com.nat.forumhub.domain.resposta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {
    Page<Resposta> findAllByTopicoId(Pageable paginacao, Long topicoId);
    boolean existsByMensagemAndSolucao(String mensagem, String solucao);
}
