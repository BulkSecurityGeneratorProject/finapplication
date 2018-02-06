package br.com.digidev.service;

import br.com.digidev.domain.Categoria;
import br.com.digidev.domain.User;
import br.com.digidev.repository.CategoriaRepository;
import br.com.digidev.repository.UserRepository;
import br.com.digidev.security.SecurityUtils;
import br.com.digidev.service.dto.CategoriaDTO;
import br.com.digidev.service.mapper.CategoriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Categoria.
 */
@Service
@Transactional
public class CategoriaService {

    private final Logger log = LoggerFactory.getLogger(CategoriaService.class);

    private final CategoriaRepository categoriaRepository;

    private final UserRepository userRepository;

    private final CategoriaMapper categoriaMapper;
    public CategoriaService(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper, UserRepository userRepository) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a categoria.
     *
     * @param categoriaDTO the entity to save
     * @return the persisted entity
     */
    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        log.debug("Request to save Categoria : {}", categoriaDTO);
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        if(null == categoria.getUser()) {
            categoria.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
        }
        categoria = categoriaRepository.save(categoria);
        return categoriaMapper.toDto(categoria);
    }

    /**
     *  Get all the categorias.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CategoriaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Categorias");
        return categoriaRepository.findAllByUserLogin(pageable, SecurityUtils.getCurrentUserLogin())
            .map(categoriaMapper::toDto);
    }

    /**
     *  Get one categoria by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CategoriaDTO findOne(Long id) {
        log.debug("Request to get Categoria : {}", id);
        Categoria categoria = categoriaRepository.findOneByIdAndUserLogin(id, SecurityUtils.getCurrentUserLogin());
        return categoriaMapper.toDto(categoria);
    }

    /**
     *  Delete the  categoria by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Categoria : {}", id);
        categoriaRepository.deleteByIdAndUserLogin(id, SecurityUtils.getCurrentUserLogin());
    }
}
