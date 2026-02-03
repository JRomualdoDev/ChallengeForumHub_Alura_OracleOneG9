package br.com.alura.forumhub.controller;

import br.com.alura.forumhub.controller.dto.resposta.RespostaDTO;
import br.com.alura.forumhub.controller.dto.topico.DadosCadastroTopico;
import br.com.alura.forumhub.domain.resposta.Resposta;
import br.com.alura.forumhub.domain.topico.Topico;
import br.com.alura.forumhub.controller.dto.topico.TopicoDTO;
import br.com.alura.forumhub.domain.usuario.Usuario;
import br.com.alura.forumhub.service.topico.TopicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoService topicoService;

    public TopicoController(TopicoService topicoService) {
        this.topicoService = topicoService;
    }

    @PostMapping
    public ResponseEntity<TopicoDTO> cadastrarTopico(
            @Valid @RequestBody DadosCadastroTopico dadosCadastroTopico,
            UriComponentsBuilder uriBuilder,
            @AuthenticationPrincipal Usuario usuarioLogado
    ){

        Topico topico = topicoService.cadastraTopico(usuarioLogado, dadosCadastroTopico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new TopicoDTO(
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getAutor().getNome(),
                topico.getCurso().getNome()
        ));
    }

    @GetMapping
    public ResponseEntity<List<TopicoDTO>> listarTopicos(
            @PageableDefault(page = 0, size = 10, sort = "dataCriacao", direction = Sort.Direction.ASC)
            Pageable pageable,
            @RequestParam(required = false) String nomeCurso,
            @RequestParam(required = false) Integer ano
    ){

        return ResponseEntity.ok(topicoService.obterTopicos(pageable, nomeCurso, ano));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDTO> buscarTopico(@PathVariable Long id){

        return ResponseEntity.ok(topicoService.listaTopico(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoDTO> atualizarTopico(@PathVariable Long id, @Valid @RequestBody TopicoDTO topico){

        return ResponseEntity.ok(topicoService.atualizaTopico(id, topico));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removerTopico(@PathVariable Long id){

        topicoService.removeTopico(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/respostas")
    public ResponseEntity<List<RespostaDTO>> listarRespostasTopicos(
            @PathVariable Long id,
            @PageableDefault(page = 0, size = 10, sort = "dataCriacao", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        System.out.println(id);
        return ResponseEntity.ok().body(topicoService.listaRespostasTopico(id, pageable));
    }
}
