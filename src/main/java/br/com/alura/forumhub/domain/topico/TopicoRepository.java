package br.com.alura.forumhub.domain.topico;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TopicoRepository extends CrudRepository<Topico, Long> {

    boolean existsByTitulo(@NotNull String titulo);


    boolean existsByMensagem(@NotNull String mensagem);

    Page<Topico> findAll(Pageable pageable);

    @Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso AND YEAR(t.dataCriacao) = :ano")
    List<Topico> findByCursoNomeEAno(String nomeCurso, int ano);
}
