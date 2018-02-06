package br.com.digidev.service;

import br.com.digidev.config.Constants;
import br.com.digidev.domain.Lancamento;
import br.com.digidev.domain.enumeration.Tipo;
import br.com.digidev.repository.LancamentoRepository;
import br.com.digidev.repository.UserRepository;
import br.com.digidev.security.SecurityUtils;
import br.com.digidev.service.dto.LancamentoDTO;
import br.com.digidev.service.dto.dashboard.GraficoSimplesDashboardDTO;
import br.com.digidev.service.dto.dashboard.GraficoSimplesDashboardTipoEnumDTO;
import br.com.digidev.service.mapper.LancamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;


/**
 * Service Implementation for managing Lancamento.
 */
@Service
@Transactional
public class LancamentoService {

    private final Logger log = LoggerFactory.getLogger(LancamentoService.class);

    private final LancamentoRepository lancamentoRepository;

    private final UserRepository userRepository;

    private final LancamentoMapper lancamentoMapper;
    public LancamentoService(LancamentoRepository lancamentoRepository,
                             LancamentoMapper lancamentoMapper,
                             UserRepository userRepository) {
        this.lancamentoRepository = lancamentoRepository;
        this.lancamentoMapper = lancamentoMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a lancamento.
     *
     * @param lancamentoDTO the entity to save
     * @return the persisted entity
     */
    public LancamentoDTO save(LancamentoDTO lancamentoDTO) {
        log.debug("Request to save Lancamento : {}", lancamentoDTO);
        Lancamento lancamento = lancamentoMapper.toEntity(lancamentoDTO);
        if(null == lancamento.getUser()) {
            lancamento.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        }
        if(null != lancamentoDTO.isRecorrente() && lancamentoDTO.isRecorrente()){
            lancamento.setParcela(1);
        }
        lancamento = lancamentoRepository.save(lancamento);
        if(null != lancamentoDTO.isRecorrente() && lancamentoDTO.isRecorrente()){
            for(int i = Constants.UM; i < lancamentoDTO.getQuantidadeParcelas(); i++){
                Lancamento lancamentoRec = lancamentoMapper.toEntity(lancamentoDTO);
                lancamentoRec.setData(lancamento.getData().plusMonths(i));
                lancamentoRec.setParcela(i+ Constants.UM);
                lancamentoRec.setLancamento(lancamento);
                lancamentoRec.setUser(lancamento.getUser());
                lancamentoRec.setPagoRecebido(Boolean.FALSE);
                lancamentoRepository.save(lancamentoRec);
            }
        }
        return lancamentoMapper.toDto(lancamento);
    }

    /**
     *  Get all the lancamentos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LancamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lancamentos");
        return lancamentoRepository.findAllByUserLogin(pageable, SecurityUtils.getCurrentUserLogin())
            .map(lancamentoMapper::toDto);
    }

    /**
     *  Get one lancamento by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public LancamentoDTO findOne(Long id) {
        log.debug("Request to get Lancamento : {}", id);
        Lancamento lancamento = lancamentoRepository.findOneByIdAndUserLogin(id, SecurityUtils.getCurrentUserLogin());
        return lancamentoMapper.toDto(lancamento);
    }

    /**
     *  Delete the  lancamento by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Lancamento : {}", id);
        lancamentoRepository.deleteByIdAndUserLogin(id, SecurityUtils.getCurrentUserLogin());
    }

    /**
     * Verifica se ah lancamentos vinculados
     * @param lancamentoId id do lancamento pai
     * @return true se existir
     */
    public boolean isLancamentosVinculados(Long lancamentoId){
        return lancamentoRepository.countByLancamentoId(lancamentoId) > 0;
    }


    /**
     * Retorna os lancamentos pelo tipo e pela data selecionada
     * @param tipo tipo receita ou despesa
     * @param dataSelecionada data selecionada
     * @return lista de lancamentos
     */
    public List<LancamentoDTO> retornaLancamentosPorTipoData(Tipo tipo, LocalDate dataSelecionada){
        List<Lancamento> list = lancamentoRepository.findAllByTipoAndDataBetweenAndUserLoginOrderByDataAsc(tipo,
            dataSelecionada.withDayOfMonth(Constants.UM), dataSelecionada.withDayOfMonth(dataSelecionada.lengthOfMonth()),
            SecurityUtils.getCurrentUserLogin());
        return lancamentoMapper.toDto(list);
    }

    /**
     * Recebe os requests agrupados por Categoria
     * @return lista de GraficoSimplesDashboardDTO.
     */
    @Transactional(readOnly = true)
    public List<GraficoSimplesDashboardDTO> getAllByDateGroupByCategoria(LocalDate dataSelecionada, Tipo tipo) {
        log.debug("Request to get all Requests group by categoria");
        LocalDate startDate = dataSelecionada.with(firstDayOfMonth());
        LocalDate endDate = dataSelecionada.with(lastDayOfMonth());
        return lancamentoRepository.getAllByDateGroupByCategoria(startDate, endDate, tipo);
    }

    /**
     * Recebe os requests agrupados por Categoria
     * @return lista de GraficoSimplesDashboardDTO.
     */
    @Transactional(readOnly = true)
    public List<GraficoSimplesDashboardTipoEnumDTO> getAllByDateGroupByTipo(LocalDate dataSelecionada) {
        log.debug("Request to get all Requests group by tipo");
        LocalDate startDate = dataSelecionada.with(firstDayOfMonth());
        LocalDate endDate = dataSelecionada.with(lastDayOfMonth());
        return lancamentoRepository.getAllByDateGroupByTipo(startDate, endDate);
    }
}
