package br.com.digidev.service;

import br.com.digidev.domain.Conta;
import br.com.digidev.domain.enumeration.Tipo;
import br.com.digidev.repository.ContaRepository;
import br.com.digidev.repository.LancamentoRepository;
import br.com.digidev.repository.UserRepository;
import br.com.digidev.security.SecurityUtils;
import br.com.digidev.service.dto.ContaDTO;
import br.com.digidev.service.dto.dashboard.GraficoSimplesDashboardDTO;
import br.com.digidev.service.mapper.ContaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Service Implementation for managing Conta.
 */
@Service
@Transactional
public class ContaService {

    private final Logger log = LoggerFactory.getLogger(ContaService.class);

    private final ContaRepository contaRepository;

    private final UserRepository userRepository;

    private final LancamentoRepository lancamentoRepository;

    private final ContaMapper contaMapper;
    public ContaService(ContaRepository contaRepository, ContaMapper contaMapper, UserRepository userRepository, LancamentoRepository lancamentoRepository) {
        this.contaRepository = contaRepository;
        this.contaMapper = contaMapper;
        this.userRepository = userRepository;
        this.lancamentoRepository = lancamentoRepository;
    }

    /**
     * Save a conta.
     *
     * @param contaDTO the entity to save
     * @return the persisted entity
     */
    public ContaDTO save(ContaDTO contaDTO) {
        log.debug("Request to save Conta : {}", contaDTO);
        Conta conta = contaMapper.toEntity(contaDTO);
        if(null == conta.getUser()) {
            conta.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        }
        conta = contaRepository.save(conta);
        return contaMapper.toDto(conta);
    }

    /**
     *  Get all the contas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ContaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contas");
        return contaRepository.findAllByUserLogin(pageable, SecurityUtils.getCurrentUserLogin())
            .map(contaMapper::toDto);
    }

    /**
     *  Get one conta by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ContaDTO findOne(Long id) {
        log.debug("Request to get Conta : {}", id);
        Conta conta = contaRepository.findOneByIdAndUserLogin(id, SecurityUtils.getCurrentUserLogin());
        return contaMapper.toDto(conta);
    }

    /**
     *  Delete the  conta by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Conta : {}", id);
        contaRepository.deleteByIdAndUserLogin(id, SecurityUtils.getCurrentUserLogin());
    }

    /**
     * Retorna a lista para o grafico de saldos de contas
     * @return
     */
    public List<GraficoSimplesDashboardDTO> listaContasSaldos(){
        List<GraficoSimplesDashboardDTO> listaContasGraficos = new ArrayList<>();
        contaRepository.findAll().forEach(conta -> {
            ContaDTO receitas = lancamentoRepository.getSumLancamentosGroupByContaAndTipo(Tipo.RECEITA, conta.getId());
            ContaDTO despesas = lancamentoRepository.getSumLancamentosGroupByContaAndTipo(Tipo.DESPESA, conta.getId());
            BigDecimal total = conta.getSaldoInicial();
            if(null != receitas){
                total = total.add(receitas.getValorTotalTipo());
            }

            if(null != despesas){
                total = total.subtract(despesas.getValorTotalTipo());
            }

            GraficoSimplesDashboardDTO graficoSimplesDashboardDTO = new GraficoSimplesDashboardDTO(total, conta.getDescricao());
            listaContasGraficos.add(graficoSimplesDashboardDTO);
        });
        return listaContasGraficos;
    }


}
