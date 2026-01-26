package br.com.alura.forumhub.controller;

import br.com.alura.forumhub.controller.dto.autenticacao.DadosTokenJWT;
import br.com.alura.forumhub.controller.dto.autenticacao.DadosAutenticacao;
import br.com.alura.forumhub.domain.usuario.Usuario;
import br.com.alura.forumhub.service.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<DadosTokenJWT> login(@RequestBody @Valid DadosAutenticacao dados) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var authentication = authenticationManager.authenticate(authenticationToken);


        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return  ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
