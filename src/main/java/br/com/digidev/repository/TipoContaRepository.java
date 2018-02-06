package br.com.digidev.repository;

import br.com.digidev.domain.TipoConta;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TipoConta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoContaRepository extends CustomUserRepository<TipoConta, Long> {

}
