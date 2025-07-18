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

    String mensagem;
    String solucao;
    LocalDateTime dataCriacao;

    @ManyToOne
    // um autor pode ter muitas respostas
    Usuario autor;

    @ManyToOne
    // um topico pode estar em muitas respostas
    Topico topico;
}
