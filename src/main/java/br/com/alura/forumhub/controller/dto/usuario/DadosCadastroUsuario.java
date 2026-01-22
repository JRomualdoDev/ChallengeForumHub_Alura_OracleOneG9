package br.com.alura.forumhub.controller.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroUsuario(
        @NotNull
        String nome,
        @NotNull
        @Email(message = "Formato de email inválido.")
        String email,
        @NotNull
        String senha
) {
}
