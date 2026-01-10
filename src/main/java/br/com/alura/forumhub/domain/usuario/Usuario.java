package br.com.alura.forumhub.domain.usuario;

import br.com.alura.forumhub.domain.perfil.Perfil;
import br.com.alura.forumhub.domain.resposta.Resposta;
import br.com.alura.forumhub.domain.topico.Topico;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;

    @OneToMany
    private Set<Topico> topicos;

    @OneToMany
    private Set<Resposta> respostas;

    @ManyToMany
    @JoinTable(
            name = "usuario_perfil",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id")
    )
    private Set<Perfil> perfis;
}
