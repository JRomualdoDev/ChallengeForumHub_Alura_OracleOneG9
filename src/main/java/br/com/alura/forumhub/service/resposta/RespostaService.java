package br.com.alura.forumhub.service.resposta;

import br.com.alura.forumhub.controller.dto.resposta.DadosCadastroResposta;
import br.com.alura.forumhub.controller.dto.resposta.DadosEditarResposta;
import br.com.alura.forumhub.controller.dto.resposta.RespostaDTO;
import br.com.alura.forumhub.domain.resposta.Resposta;
import br.com.alura.forumhub.domain.resposta.RespostaRepository;
import br.com.alura.forumhub.domain.topico.Topico;
import br.com.alura.forumhub.domain.topico.TopicoRepository;
import br.com.alura.forumhub.domain.usuario.Usuario;
import br.com.alura.forumhub.infra.exception.ErroControllerException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RespostaService {

    private final RespostaRepository respostaRepository;
    private final TopicoRepository topicoRepository;

    public RespostaService(
            RespostaRepository respostaRepository,
            TopicoRepository topicoRepository
    ) {
        this.respostaRepository = respostaRepository;
        this.topicoRepository = topicoRepository;
    }


    @Transactional
    public Resposta cadastroResposta(Usuario usuarioLogado, DadosCadastroResposta dataCadastroResposta) {

        Topico topico = topicoRepository.findById(dataCadastroResposta.topicoId()).orElseThrow( () -> new ErroControllerException("Tópico", "Tópico não encontrado."));

        Resposta resposta = new Resposta(
                dataCadastroResposta.mensagem(),
                topico,
                usuarioLogado,
                dataCadastroResposta.solucao()
        );

        return respostaRepository.save(resposta);
    }

    @Transactional
    public RespostaDTO listaResposta(Long id) {

        Resposta resposta = respostaRepository.findById(id).orElseThrow(() -> new ErroControllerException("Resposta", "Resposta inexistente."));

        return new RespostaDTO(
                resposta.getMensagem(),
                resposta.getTopico().getTitulo(),
                resposta.getAutor().getNome(),
                resposta.getDataCriacao(),
                resposta.getSolucao()
        );
    }

    @Transactional
    public RespostaDTO atualizaResposta(Long id, Usuario usuarioLogado, DadosEditarResposta dadosEditarResposta) {

        Resposta resposta = respostaRepository.findById(id).orElseThrow(() -> new ErroControllerException("Resposta", "Resposta inexistente."));

        resposta.setMensagem(dadosEditarResposta.mensagem());

        return new RespostaDTO(
                resposta.getMensagem(),
                resposta.getTopico().getTitulo(),
                resposta.getAutor().getNome(),
                resposta.getDataCriacao(),
                resposta.getSolucao()
        );
    }

    public void removeResposta(Long id) {

        Resposta resposta = respostaRepository.findById(id).orElseThrow(() -> new ErroControllerException("Resposta", "Resposta inexistente."));

        respostaRepository.deleteById(id);
    }
}
