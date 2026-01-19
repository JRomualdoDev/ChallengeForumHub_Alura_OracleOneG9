package br.com.alura.forumhub.domain.curso;

import br.com.alura.forumhub.domain.topico.Topico;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter @Getter
@RequiredArgsConstructor
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;
    @NotNull
    private String categoria;

    @OneToMany(mappedBy = "curso")
    private Set<Topico> topicos = new HashSet<>();
}
