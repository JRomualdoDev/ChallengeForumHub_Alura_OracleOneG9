package br.com.alura.forumhub.controller.dto.autenticacao;

import jakarta.validation.constraints.NotNull;

public record DadosAutenticacao(
        @NotNull
        String email,
        @NotNull
        String senha
) {
}
