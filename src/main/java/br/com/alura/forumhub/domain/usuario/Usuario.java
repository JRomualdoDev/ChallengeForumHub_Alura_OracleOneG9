package br.com.alura.forumhub.domain.usuario;

import br.com.alura.forumhub.domain.perfil.Perfil;
import br.com.alura.forumhub.domain.resposta.Resposta;
import br.com.alura.forumhub.domain.topico.StatusTopico;
import br.com.alura.forumhub.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter @Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String nome;
    @NonNull
    private String email;
    @NonNull
    private String senha;

    @OneToMany(mappedBy = "autor")
    private Set<Topico> topicos = new HashSet<>();

    @OneToMany(mappedBy = "autor")
    private Set<Resposta> respostas = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_perfil",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id")
    )
    private Set<Perfil> perfis = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.perfis;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return nome;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void atualizarInformacoes(String nome, String email, String senha) {

        if (nome != null) {
            this.nome = nome;
        }

        if (email != null) {
            this.email = email;
        }

        if (senha != null) {
            this.senha = senha;
        }
    }
}
