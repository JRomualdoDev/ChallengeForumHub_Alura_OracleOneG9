package br.com.alura.forumhub.controller;

import br.com.alura.forumhub.controller.dto.resposta.DadosCadastroResposta;
import br.com.alura.forumhub.controller.dto.resposta.DadosEditarResposta;
import br.com.alura.forumhub.controller.dto.resposta.RespostaDTO;
import br.com.alura.forumhub.domain.resposta.Resposta;
import br.com.alura.forumhub.domain.usuario.Usuario;
import br.com.alura.forumhub.service.resposta.RespostaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/respostas")
public class RespostaController {

    private final RespostaService respostaService;

    public RespostaController(RespostaService respostaService) {
        this.respostaService = respostaService;
    }

    @PostMapping
    public ResponseEntity<RespostaDTO> cadastrarResposta(
            @Valid
            @RequestBody DadosCadastroResposta dadosCadastroResposta,
            UriComponentsBuilder uriBuilder,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {

        Resposta resposta = respostaService.cadastroResposta(usuarioLogado, dadosCadastroResposta);

        URI uri = uriBuilder.path("/resposta/{id}").buildAndExpand(resposta.getId()).toUri();

        return ResponseEntity.created(uri).body(new RespostaDTO(
                resposta.getMensagem(),
                resposta.getTopico().getTitulo(),
                resposta.getAutor().getNome(),
                resposta.getDataCriacao(),
                resposta.getSolucao()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaDTO> listaResposta(@PathVariable Long id){

        return ResponseEntity.ok(respostaService.listaResposta(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespostaDTO> atualizarResposta(
            @Valid
            @PathVariable Long id,
            @RequestBody DadosEditarResposta dadosEditarResposta,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {

        return ResponseEntity.ok().body(respostaService.atualizaResposta(id, usuarioLogado, dadosEditarResposta));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removerResposta(@PathVariable Long id){

        respostaService.removeResposta(id);

        return ResponseEntity.noContent().build();
    }
}
