package br.com.digidev.repository;

import br.com.digidev.domain.Categoria;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Categoria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriaRepository extends CustomUserRepository<Categoria, Long> {

}
