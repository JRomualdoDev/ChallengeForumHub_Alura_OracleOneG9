package br.com.alura.forumhub.domain.resposta;

import br.com.alura.forumhub.domain.topico.Topico;
import br.com.alura.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String mensagem;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "topico_id", nullable = false)
    private Topico topico;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    @NonNull
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario autor;

    @NonNull
    private Boolean solucao;
}
