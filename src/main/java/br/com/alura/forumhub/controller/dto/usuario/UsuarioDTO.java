package br.com.alura.forumhub.controller.dto.usuario;

public record UsuarioDTO(
        String nome,
        String email
) {
    public UsuarioDTO(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }
}
