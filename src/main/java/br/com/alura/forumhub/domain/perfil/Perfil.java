package br.com.alura.forumhub.domain.perfil;

import br.com.alura.forumhub.domain.topico.Topico;
import br.com.alura.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToMany(mappedBy = "perfis")
    private Set<Usuario> usuarios = new HashSet<>();

}
