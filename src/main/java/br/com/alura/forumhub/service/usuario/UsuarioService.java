package br.com.alura.forumhub.service.usuario;

import br.com.alura.forumhub.controller.dto.usuario.DadosCadastroUsuario;
import br.com.alura.forumhub.controller.dto.usuario.DadosEditarUsuario;
import br.com.alura.forumhub.controller.dto.usuario.UsuarioDTO;
import br.com.alura.forumhub.domain.perfil.Perfil;
import br.com.alura.forumhub.domain.perfil.PerfilRepository;
import br.com.alura.forumhub.domain.usuario.Usuario;
import br.com.alura.forumhub.domain.usuario.UsuarioRepository;
import br.com.alura.forumhub.infra.exception.ErroControllerException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder encoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PerfilRepository perfilRepository,
            PasswordEncoder encoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.encoder = encoder;
    }

    @Transactional
    public Usuario cadastrarUsuario(DadosCadastroUsuario usuarioDados) {

        String senha = encoder.encode(usuarioDados.senha());

        Usuario usuario = new Usuario(
                usuarioDados.nome(),
                usuarioDados.email(),
                senha
        );

        Perfil perfilPadrao = perfilRepository.findByNome("ROLE_USUARIO").orElseThrow(() -> new ErroControllerException("Perfil", "Perfil não encontrado."));

        usuario.getPerfis().add(perfilPadrao);

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public UsuarioDTO editarUsuario(Long id, DadosEditarUsuario dadosEditarUsuario) {

        Usuario  usuario = usuarioRepository.findById(id).orElseThrow( () -> new ErroControllerException("Usuário", "Usuário não encontrado."));

        String senha = null;

        if (dadosEditarUsuario.senha() != null) {
            senha = encoder.encode(dadosEditarUsuario.senha());
        }

        usuario.atualizarInformacoes(dadosEditarUsuario.nome(), dadosEditarUsuario.email(), senha);

        return new UsuarioDTO(usuario.getNome(), usuario.getEmail());
    }

    public List<Usuario> listaUsuarios(Pageable pageable) {

        List<Usuario> usuarios = usuarioRepository.findAll(pageable).getContent();

        return usuarios;
    }
}
