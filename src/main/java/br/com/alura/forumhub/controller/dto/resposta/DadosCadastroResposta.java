package br.com.alura.forumhub.controller.dto.resposta;

public record DadosCadastroResposta(
        String mensagem,
        Long topicoId,
        boolean solucao
) {
}
