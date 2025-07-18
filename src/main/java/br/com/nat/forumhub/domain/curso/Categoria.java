package br.com.nat.forumhub.domain.curso;

public enum Categoria {
    PROGRAMACAO("Programação"),
    FRONTEND("Front-end"),
    INTELIGENCIA_ARTIFICIAL("Inteligência Artificial"),
    UX_DESIGN("UX e Design"),
    DEVOPS("DevOps"),
    MOBILE("Mobile"),
    DATA_SCIENCE("Data Science"),
    INOVACAO_GESTAO("Inovação e Gestão");

    private final String descricao;

    Categoria(String categoria) {
        this.descricao = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Categoria fromDescricao(String descricao){
        for (Categoria categoria: Categoria.values()){
            if (categoria.getDescricao().contains(descricao)){
                return categoria;
            }
        }

        throw new IllegalArgumentException("Descrição inválida: " + descricao);
    }
}
