package br.com.nat.forumhub.domain.curso;

public record CursoDadosDetalhados(
        Long id,
        String nome,
        Categoria categoria
) {
    public CursoDadosDetalhados(Curso curso){
        this(curso.getId(), curso.nome, curso.getCategoria());
    }
}
