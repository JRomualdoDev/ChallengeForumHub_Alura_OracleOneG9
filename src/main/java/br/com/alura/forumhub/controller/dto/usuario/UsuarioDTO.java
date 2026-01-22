package br.com.alura.forumhub.controller.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UsuarioDTO(
        String nome,
        String email
) {
}
