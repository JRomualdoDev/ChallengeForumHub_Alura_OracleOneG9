package br.com.alura.forumhub.controller.dto.resposta;

import java.time.LocalDateTime;

public record RespostaDTO(
        String mensagem,
        String topicoNome,
        String nomeAutor,
        LocalDateTime dataCriacao,
        boolean solucao
) {
}
