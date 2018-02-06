package br.com.digidev.repository;

import br.com.digidev.domain.Lancamento;
import br.com.digidev.domain.enumeration.Tipo;
import br.com.digidev.service.dto.ContaDTO;
import br.com.digidev.service.dto.dashboard.GraficoSimplesDashboardDTO;
import br.com.digidev.service.dto.dashboard.GraficoSimplesDashboardTipoEnumDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the Lancamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LancamentoRepository extends CustomUserRepository<Lancamento, Long> {

    Long countByLancamentoId(Long lancamentoId);

    List<Lancamento> findAllByTipoAndDataBetweenAndUserLoginOrderByDataAsc(Tipo tipo, LocalDate dataInicio, LocalDate dataFim, String login);

    @Query(value = "select new br.com.digidev.service.dto.dashboard.GraficoSimplesDashboardDTO(sum(l.valor), l.categoria.descricao) from Lancamento l where l.user.login = ?#{principal.username} AND l.tipo = :tipo AND l.data BETWEEN :startDate AND :endDate group by l.categoria")
    List<GraficoSimplesDashboardDTO> getAllByDateGroupByCategoria(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("tipo") Tipo tipo);

    @Query(value = "select new br.com.digidev.service.dto.dashboard.GraficoSimplesDashboardTipoEnumDTO(sum(l.valor), l.tipo) from Lancamento l where l.user.login = ?#{principal.username} AND l.data BETWEEN :startDate AND :endDate group by l.tipo")
    List<GraficoSimplesDashboardTipoEnumDTO> getAllByDateGroupByTipo(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "select new br.com.digidev.service.dto.ContaDTO(sum(l.valor)) from Lancamento l where l.user.login = ?#{principal.username} AND l.pagoRecebido = true AND l.tipo = :tipo AND l.conta.id = :idConta group by l.tipo, l.conta")
    ContaDTO getSumLancamentosGroupByContaAndTipo(@Param("tipo") Tipo tipo, @Param("idConta") Long idConta);


}
