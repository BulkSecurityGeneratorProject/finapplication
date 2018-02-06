package br.com.digidev.repository;

import br.com.digidev.domain.Conta;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Conta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContaRepository extends CustomUserRepository<Conta, Long> {

}
