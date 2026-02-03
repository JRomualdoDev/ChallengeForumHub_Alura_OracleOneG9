package br.com.alura.forumhub.domain.perfil;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepository extends CrudRepository<Perfil, Long> {

    Optional<Perfil> findByNome(String nome);

}
