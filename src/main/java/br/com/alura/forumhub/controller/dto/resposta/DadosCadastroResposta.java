package br.com.alura.forumhub.controller.dto.resposta;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

public record DadosCadastroResposta(
        @NotNull
        String mensagem,
        @NonNull
        Long topicoId,
        @NotNull
        boolean solucao
) {
}
