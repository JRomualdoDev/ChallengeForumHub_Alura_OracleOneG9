package br.com.alura.forumhub.controller.dto.topico;

import br.com.alura.forumhub.domain.topico.StatusTopico;
import br.com.alura.forumhub.domain.topico.Topico;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

public record TopicoDTO (
    String titulo,
    String mensagem,
    LocalDateTime dataCriacao,
    @Enumerated(EnumType.STRING)
    StatusTopico status,
    String autor,
    String curso
){
    public TopicoDTO(
            String titulo,
            String mensagem,
            LocalDateTime dataCriacao,
            StatusTopico status,
            String autor,
            String curso
    ) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataCriacao = dataCriacao;
        this.status = status;
        this.autor = autor;
        this.curso = curso;
    }

    @JsonIgnore
    public boolean isEmpty(){
        return titulo == null && mensagem == null && status == null && autor == null && curso == null;
    }
}
