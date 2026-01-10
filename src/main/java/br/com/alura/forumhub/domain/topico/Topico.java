package br.com.alura.forumhub.domain.topico;

import br.com.alura.forumhub.domain.curso.Curso;
import br.com.alura.forumhub.domain.resposta.Resposta;
import br.com.alura.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="curso_id", nullable=false)
    private Curso curso;

    @OneToMany(mappedBy = "topico")
    private Set<Resposta> respostas;
}
