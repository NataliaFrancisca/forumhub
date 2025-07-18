package br.com.nat.forumhub.domain.topico;

import br.com.nat.forumhub.domain.curso.Curso;
import br.com.nat.forumhub.domain.resposta.Resposta;
import br.com.nat.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String titulo;
    @Column(nullable = false)
    String mensagem;
    @Column(nullable = false)
    LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Status status;

    @ManyToOne
    // muitos topicos para um autor.
    Usuario autor;

    @ManyToOne
    // muitos topicos para um curso.
    Curso curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.MERGE)
    // muitas respostas para um topico;
    List<Resposta> respostas;

    public Topico(TopicoRegistro topico) {
        this.titulo = topico.titulo();
        this.mensagem = topico.mensagem();
        this.dataCriacao = LocalDateTime.now();
        this.status = Status.PENDENTE;
    }

    public void atualizar(TopicoAtualizado topico) {
        if (topico.titulo() != null){
            if (!topico.titulo().isEmpty()){
                this.titulo = topico.titulo();
            }
        }

        if (topico.mensagem() != null){
            if (!topico.mensagem().isEmpty()){
                this.mensagem = topico.mensagem();
            }
        }
    }
}
