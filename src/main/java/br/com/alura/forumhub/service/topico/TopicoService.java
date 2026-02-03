package br.com.alura.forumhub.service.topico;

import br.com.alura.forumhub.controller.dto.resposta.RespostaDTO;
import br.com.alura.forumhub.controller.dto.topico.DadosCadastroTopico;
import br.com.alura.forumhub.controller.dto.topico.TopicoDTO;
import br.com.alura.forumhub.domain.curso.Curso;
import br.com.alura.forumhub.domain.curso.CursoRepository;
import br.com.alura.forumhub.domain.resposta.RespostaRepository;
import br.com.alura.forumhub.domain.topico.Topico;
import br.com.alura.forumhub.domain.topico.TopicoRepository;
import br.com.alura.forumhub.domain.usuario.Usuario;
import br.com.alura.forumhub.infra.exception.ErroControllerException;
import br.com.alura.forumhub.infra.exception.ErrosControllerException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TopicoService {

    private final CursoRepository cursoRepository;
    private final TopicoRepository topicoRepository;
    private final RespostaRepository respostaRepository;

    public TopicoService(CursoRepository repository, TopicoRepository topicoRepository, RespostaRepository respostaRepository) {
        this.cursoRepository = repository;
        this.topicoRepository = topicoRepository;
        this.respostaRepository = respostaRepository;
    }

    @Transactional
    public Topico cadastraTopico(Usuario usuarioLogado, DadosCadastroTopico topico) {

        Map<String,String> erros = new HashMap<>();

        Optional<Curso> curso = cursoRepository.findByNome(topico.curso().toLowerCase());

        if (curso.isEmpty()) {
            erros.put("Curso", "Curso não encontrado");
        }

        if (topicoRepository.existsByTitulo(topico.titulo())) {
            erros.put("Título", "Este título já está sendo usado.");
        }

        if (topicoRepository.existsByMensagem(topico.mensagem())) {
            erros.put("Mensagem", "Esta mensagem já existe.");
        }

        if(!erros.isEmpty()) {
            throw new ErrosControllerException(erros);
        }

        Topico newTopico = new Topico(
            topico.titulo(),
            topico.mensagem(),
            topico.status(),
            usuarioLogado,
            curso.get()
        );

        return topicoRepository.save(newTopico);
    }

    public List<TopicoDTO> obterTopicos(Pageable pageable, String nomeCurso, Integer ano) {

        if (nomeCurso != null && ano != null) {
            return topicoRepository.findByCursoNomeEAno(nomeCurso, ano)
                    .stream()
                    .map((t) -> new TopicoDTO(
                            t.getTitulo(),
                            t.getMensagem(),
                            t.getDataCriacao(),
                            t.getStatus(),
                            t.getAutor().getNome(),
                            t.getCurso().getNome()
                    ))
                    .toList();
        }

        return topicoRepository.findAll(pageable)
                .getContent()
                .stream()
                .map((t) -> new TopicoDTO(
                        t.getTitulo(),
                        t.getMensagem(),
                        t.getDataCriacao(),
                        t.getStatus(),
                        t.getAutor().getNome(),
                        t.getCurso().getNome()
                ))
                .toList();
    }

    @Transactional
    public TopicoDTO listaTopico(Long id) {

        Optional<Topico> topico = topicoRepository.findById(id);

        if (topico.isEmpty()) {
            throw new ErroControllerException("Tópico", "Tópico não encontrado.");
        }

        return new TopicoDTO(
                topico.get().getTitulo(),
                topico.get().getMensagem(),
                topico.get().getDataCriacao(),
                topico.get().getStatus(),
                topico.get().getAutor().getNome(),
                topico.get().getCurso().getNome()
        );
    }

    @Transactional
    public TopicoDTO atualizaTopico(Long id, TopicoDTO topico) {

        if (topico.isEmpty()) {
            throw new ErroControllerException("Json vazio", "Você precisa colocar pelo menos um campo para editar o tópico");
        }

        Topico existeTopico = topicoRepository.findById(id).orElseThrow( () -> new ErroControllerException("Topico", "Tópico não encontrado.") );

        if (topico.curso() != null) {
            Curso curso = cursoRepository.findByNome(topico.curso()).orElseThrow( () -> new ErroControllerException("Curso", "Curso não encontrado."));
            existeTopico.atualizarInfoCurso(curso);
        }


        existeTopico.atualizarInformacoes(topico.titulo(), topico.mensagem(), topico.status());

        return new TopicoDTO(
                existeTopico.getTitulo(),
                existeTopico.getMensagem(),
                existeTopico.getDataCriacao(),
                existeTopico.getStatus(),
                existeTopico.getAutor().getNome(),
                existeTopico.getCurso().getNome()
        );
    }

    public void removeTopico(Long id) {

        Topico existeTopico = topicoRepository.findById(id).orElseThrow( () -> new ErroControllerException("Topico", "Tópico não encontrado.") );

        topicoRepository.deleteById(existeTopico.getId());
    }

    @Transactional
    public List<RespostaDTO> listaRespostasTopico(Long id,  Pageable pageable) {

        if (!topicoRepository.existsById(id) ) {
            throw new ErroControllerException("Topico", "Tópico não encontrado.");
        }

        return respostaRepository.findByTopicoId(id, pageable)
                .getContent()
                .stream()
                .map(resposta -> new RespostaDTO(
                        resposta.getMensagem(),
                        resposta.getTopico().getTitulo(),
                        resposta.getAutor().getNome(),
                        resposta.getDataCriacao(),
                        resposta.getSolucao()
                ))
                .toList();
    }
}
