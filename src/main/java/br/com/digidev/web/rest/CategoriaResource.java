package br.com.digidev.web.rest;

import br.com.digidev.security.AuthoritiesConstants;
import br.com.digidev.service.UserService;
import com.codahale.metrics.annotation.Timed;
import br.com.digidev.service.CategoriaService;
import br.com.digidev.web.rest.util.HeaderUtil;
import br.com.digidev.web.rest.util.PaginationUtil;
import br.com.digidev.service.dto.CategoriaDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Categoria.
 */
@RestController
@RequestMapping("/api")
public class CategoriaResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaResource.class);

    private static final String ENTITY_NAME = "categoria";

    private final CategoriaService categoriaService;

    private final UserService userService;

    public CategoriaResource(CategoriaService categoriaService,
                             UserService userService) {
        this.categoriaService = categoriaService;
        this.userService = userService;
    }

    /**
     * POST  /categorias : Create a new categoria.
     *
     * @param categoriaDTO the categoriaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new categoriaDTO, or with status 400 (Bad Request) if the categoria has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/categorias")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<CategoriaDTO> createCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) throws URISyntaxException {
        log.debug("REST request to save Categoria : {}", categoriaDTO);
        if (categoriaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new categoria cannot already have an ID")).body(null);
        }
        CategoriaDTO result = categoriaService.save(categoriaDTO);
        return ResponseEntity.created(new URI("/api/categorias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /categorias : Updates an existing categoria.
     *
     * @param categoriaDTO the categoriaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated categoriaDTO,
     * or with status 400 (Bad Request) if the categoriaDTO is not valid,
     * or with status 500 (Internal Server Error) if the categoriaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/categorias")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<CategoriaDTO> updateCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) throws URISyntaxException {
        log.debug("REST request to update Categoria : {}", categoriaDTO);
        if (categoriaDTO.getId() == null) {
            return createCategoria(categoriaDTO);
        }

        if(!userService.isUserEntity(categoriaDTO.getUserId())){
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "acaonaopermitidausuario", "Acao nao permitida para este usuario"))
                .body(null);
        }

        CategoriaDTO result = categoriaService.save(categoriaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, categoriaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /categorias : get all the categorias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of categorias in body
     */
    @GetMapping("/categorias")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Categorias");
        Page<CategoriaDTO> page = categoriaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/categorias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /categorias/:id : get the "id" categoria.
     *
     * @param id the id of the categoriaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the categoriaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/categorias/{id}")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<CategoriaDTO> getCategoria(@PathVariable Long id) {
        log.debug("REST request to get Categoria : {}", id);
        CategoriaDTO categoriaDTO = categoriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(categoriaDTO));
    }

    /**
     * DELETE  /categorias/:id : delete the "id" categoria.
     *
     * @param id the id of the categoriaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/categorias/{id}")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        log.debug("REST request to delete Categoria : {}", id);
        categoriaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
