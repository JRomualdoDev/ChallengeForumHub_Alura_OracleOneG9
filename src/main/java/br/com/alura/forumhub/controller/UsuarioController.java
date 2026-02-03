package br.com.alura.forumhub.controller;

import br.com.alura.forumhub.controller.dto.usuario.DadosCadastroUsuario;
import br.com.alura.forumhub.controller.dto.usuario.DadosEditarUsuario;
import br.com.alura.forumhub.controller.dto.usuario.UsuarioDTO;
import br.com.alura.forumhub.domain.usuario.Usuario;
import br.com.alura.forumhub.service.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@Valid @RequestBody DadosCadastroUsuario usuarioDados, UriComponentsBuilder uriBuilder) {

        Usuario usuario = usuarioService.cadastrarUsuario(usuarioDados);

        var uri = uriBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(uri).body(new UsuarioDTO(
                usuario.getNome(),
                usuario.getEmail()
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> editarUsuario(@PathVariable Long id, @Valid @RequestBody DadosEditarUsuario dadosEditarUsuario) {

        return ResponseEntity.ok().body(usuarioService.editarUsuario(id, dadosEditarUsuario));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios(
            @PageableDefault(page = 0, size = 10, sort = "nome", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {

        List<Usuario> usuario = usuarioService.listaUsuarios(pageable);

        return ResponseEntity.ok().body(
                usuario.stream()
                        .map((u) -> new UsuarioDTO(
                                u.getNome(),
                                u.getEmail()
                        ))
                        .toList()
        );
    }
}
