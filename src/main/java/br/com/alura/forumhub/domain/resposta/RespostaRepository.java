package br.com.alura.forumhub.domain.resposta;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespostaRepository extends CrudRepository<Resposta, Long> {
}
