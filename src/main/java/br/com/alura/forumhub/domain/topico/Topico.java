package br.com.alura.forumhub.domain.topico;

import br.com.alura.forumhub.domain.curso.Curso;
import br.com.alura.forumhub.domain.resposta.Resposta;
import br.com.alura.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String titulo;
    @NonNull
    private String mensagem;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    @NonNull
    @Enumerated(EnumType.STRING)
    private StatusTopico status;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario autor;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="curso_id", nullable=false)
    private Curso curso;

    @NonNull
    @OneToMany(mappedBy = "topico")
    private Set<Resposta> respostas = new HashSet<>();

    public void atualizarInformacoes(String titulo, String mensagem, StatusTopico status) {

        if (titulo != null) {
            this.titulo = titulo;
        }

        if (mensagem != null) {
            this.mensagem = mensagem;
        }

        if (status != null) {
            this.status = status;
        }
    }

    public void  atualizarInfoCurso(Curso curso) {
        if (curso != null) {
            this.curso = curso;
        }
    }
}
