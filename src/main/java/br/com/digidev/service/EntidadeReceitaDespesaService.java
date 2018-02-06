package br.com.digidev.service;

import br.com.digidev.domain.EntidadeReceitaDespesa;
import br.com.digidev.repository.EntidadeReceitaDespesaRepository;
import br.com.digidev.repository.UserRepository;
import br.com.digidev.security.SecurityUtils;
import br.com.digidev.service.dto.EntidadeReceitaDespesaDTO;
import br.com.digidev.service.mapper.EntidadeReceitaDespesaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing EntidadeReceitaDespesa.
 */
@Service
@Transactional
public class EntidadeReceitaDespesaService {

    private final Logger log = LoggerFactory.getLogger(EntidadeReceitaDespesaService.class);

    private final EntidadeReceitaDespesaRepository entidadeReceitaDespesaRepository;

    private final UserRepository userRepository;

    private final EntidadeReceitaDespesaMapper entidadeReceitaDespesaMapper;
    public EntidadeReceitaDespesaService(EntidadeReceitaDespesaRepository entidadeReceitaDespesaRepository,
                                         EntidadeReceitaDespesaMapper entidadeReceitaDespesaMapper,
                                         UserRepository userRepository) {
        this.entidadeReceitaDespesaRepository = entidadeReceitaDespesaRepository;
        this.entidadeReceitaDespesaMapper = entidadeReceitaDespesaMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a entidadeReceitaDespesa.
     *
     * @param entidadeReceitaDespesaDTO the entity to save
     * @return the persisted entity
     */
    public EntidadeReceitaDespesaDTO save(EntidadeReceitaDespesaDTO entidadeReceitaDespesaDTO) {
        log.debug("Request to save EntidadeReceitaDespesa : {}", entidadeReceitaDespesaDTO);
        EntidadeReceitaDespesa entidadeReceitaDespesa = entidadeReceitaDespesaMapper.toEntity(entidadeReceitaDespesaDTO);
        if(null == entidadeReceitaDespesa.getUser()) {
            entidadeReceitaDespesa.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        }
        entidadeReceitaDespesa = entidadeReceitaDespesaRepository.save(entidadeReceitaDespesa);
        return entidadeReceitaDespesaMapper.toDto(entidadeReceitaDespesa);
    }

    /**
     *  Get all the entidadeReceitaDespesas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EntidadeReceitaDespesaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EntidadeReceitaDespesas");
        return entidadeReceitaDespesaRepository.findAllByUserLogin(pageable, SecurityUtils.getCurrentUserLogin())
            .map(entidadeReceitaDespesaMapper::toDto);
    }

    /**
     *  Get one entidadeReceitaDespesa by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public EntidadeReceitaDespesaDTO findOne(Long id) {
        log.debug("Request to get EntidadeReceitaDespesa : {}", id);
        EntidadeReceitaDespesa entidadeReceitaDespesa = entidadeReceitaDespesaRepository.findOneByIdAndUserLogin(id, SecurityUtils.getCurrentUserLogin());
        return entidadeReceitaDespesaMapper.toDto(entidadeReceitaDespesa);
    }

    /**
     *  Delete the  entidadeReceitaDespesa by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EntidadeReceitaDespesa : {}", id);
        entidadeReceitaDespesaRepository.deleteByIdAndUserLogin(id, SecurityUtils.getCurrentUserLogin());
    }
}
