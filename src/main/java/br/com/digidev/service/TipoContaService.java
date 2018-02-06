package br.com.digidev.service;

import br.com.digidev.domain.TipoConta;
import br.com.digidev.repository.TipoContaRepository;
import br.com.digidev.repository.UserRepository;
import br.com.digidev.security.SecurityUtils;
import br.com.digidev.service.dto.TipoContaDTO;
import br.com.digidev.service.mapper.TipoContaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TipoConta.
 */
@Service
@Transactional
public class TipoContaService {

    private final Logger log = LoggerFactory.getLogger(TipoContaService.class);

    private final TipoContaRepository tipoContaRepository;

    private final UserRepository userRepository;

    private final TipoContaMapper tipoContaMapper;
    public TipoContaService(TipoContaRepository tipoContaRepository,
                            TipoContaMapper tipoContaMapper,
                            UserRepository userRepository) {
        this.tipoContaRepository = tipoContaRepository;
        this.tipoContaMapper = tipoContaMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a tipoConta.
     *
     * @param tipoContaDTO the entity to save
     * @return the persisted entity
     */
    public TipoContaDTO save(TipoContaDTO tipoContaDTO) {
        log.debug("Request to save TipoConta : {}", tipoContaDTO);
        TipoConta tipoConta = tipoContaMapper.toEntity(tipoContaDTO);
        if(null == tipoConta.getUser()) {
            tipoConta.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        }
        tipoConta = tipoContaRepository.save(tipoConta);
        return tipoContaMapper.toDto(tipoConta);
    }

    /**
     *  Get all the tipoContas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TipoContaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoContas");
        return tipoContaRepository.findAllByUserLogin(pageable, SecurityUtils.getCurrentUserLogin())
            .map(tipoContaMapper::toDto);
    }

    /**
     *  Get one tipoConta by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public TipoContaDTO findOne(Long id) {
        log.debug("Request to get TipoConta : {}", id);
        TipoConta tipoConta = tipoContaRepository.findOneByIdAndUserLogin(id, SecurityUtils.getCurrentUserLogin());
        return tipoContaMapper.toDto(tipoConta);
    }

    /**
     *  Delete the  tipoConta by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoConta : {}", id);
        tipoContaRepository.deleteByIdAndUserLogin(id, SecurityUtils.getCurrentUserLogin());
    }
}
