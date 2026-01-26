package br.com.alura.forumhub.controller.dto.resposta;

import br.com.alura.forumhub.domain.resposta.Resposta;

import java.time.LocalDateTime;

public record RespostaDTO(
        String mensagem,
        String topicoNome,
        String nomeAutor,
        LocalDateTime dataCriacao,
        boolean solucao
) {
    public RespostaDTO(
            String mensagem,
            String topicoNome,
            String nomeAutor,
            LocalDateTime dataCriacao,
            boolean solucao
    ) {
        this.mensagem = mensagem;
        this.topicoNome = topicoNome;
        this.nomeAutor = nomeAutor;
        this.dataCriacao = dataCriacao;
        this.solucao = solucao;
    }
}
