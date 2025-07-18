package br.com.nat.forumhub.domain.curso;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Curso")
@Table(name = "cursos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String nome;

    @Enumerated(EnumType.STRING)
    Categoria categoria;

    public Curso(CursoRegistro curso){
        this.nome = curso.nome();
        this.categoria = Categoria.fromDescricao(curso.categoria());
    }
}
