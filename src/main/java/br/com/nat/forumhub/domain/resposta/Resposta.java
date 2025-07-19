package br.com.nat.forumhub.domain.resposta;

import br.com.nat.forumhub.domain.topico.Topico;
import br.com.nat.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "Resposta")
@Table(name = "respostas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String mensagem;
    @Column(nullable = false)
    String solucao;
    @Column(nullable = false)
    LocalDateTime dataCriacao;

    @ManyToOne
    // um autor pode ter muitas respostas
    Usuario autor;

    @ManyToOne
    // um topico pode estar em muitas respostas
    Topico topico;

    public Resposta(RespostaRegistro resposta) {
        this.mensagem = resposta.mensagem();
        this.solucao = resposta.solucao();
        this.dataCriacao = LocalDateTime.now();
    }

    public void atualizar(RespostaAtualizada resposta) {
        if (resposta.mensagem() != null){
            if (!resposta.mensagem().isEmpty()){
                this.mensagem = resposta.mensagem();
            }
        }

        if (resposta.solucao() != null){
            if (!resposta.solucao().isEmpty()){
                this.solucao = resposta.solucao();
            }
        }
    }
}
