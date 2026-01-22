package br.com.alura.forumhub.controller.dto.usuario;

public record DadosEditarUsuario(
        String nome,
        String email,
        String senha
) {
}
