package br.com.digidev.repository;

import br.com.digidev.domain.EntidadeReceitaDespesa;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EntidadeReceitaDespesa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntidadeReceitaDespesaRepository extends CustomUserRepository<EntidadeReceitaDespesa, Long> {

}
