package br.com.alura.forumhub.controller.dto.topico;

import br.com.alura.forumhub.domain.resposta.Resposta;
import br.com.alura.forumhub.domain.topico.StatusTopico;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record DadosCadastroTopico(

        @NotNull
        String titulo,
        @NotNull
        String mensagem,

        @NotNull
        @Enumerated(EnumType.STRING)
        StatusTopico status,

        @NotNull
        String curso,

        Set<Resposta> respostas

) {
}
