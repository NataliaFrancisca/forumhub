package br.com.nat.forumhub.domain.usuario.autenticacao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioAutenticacao(
        @NotBlank(message = "O campo e-mail é obrigatório.")
        @Email(message = "Digite um e-mail válido.")
        String email,
        @NotBlank(message = "O campo senha é obrigatório.")
        @Size(min = 6, message = "Digite uma senha válida, com no mínimo 6 caracteres.")
        String senha
) {
}
