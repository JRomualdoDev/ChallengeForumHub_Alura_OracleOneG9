package br.com.alura.forumhub.domain.curso;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CursoRepository extends CrudRepository<Curso, Long> {

    Optional<Curso> findByNome(String nome);

}
