package br.com.nat.forumhub.domain.usuario;

public record UsuarioDadosDetalhados(
        Long id,
        String nome,
        String email,
        Perfil perfil
) {
    public UsuarioDadosDetalhados(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getPerfil());
    }
}
