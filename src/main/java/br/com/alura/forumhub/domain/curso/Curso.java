package br.com.alura.forumhub.domain.curso;

import br.com.alura.forumhub.domain.topico.Topico;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private String categoria;

    @OneToMany(mappedBy = "curso")
    private Set<Topico> topicos;
}
