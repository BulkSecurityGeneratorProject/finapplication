package br.com.digidev.web.rest;

import br.com.digidev.security.AuthoritiesConstants;
import br.com.digidev.service.UserService;
import com.codahale.metrics.annotation.Timed;
import br.com.digidev.service.EntidadeReceitaDespesaService;
import br.com.digidev.web.rest.util.HeaderUtil;
import br.com.digidev.web.rest.util.PaginationUtil;
import br.com.digidev.service.dto.EntidadeReceitaDespesaDTO;
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
 * REST controller for managing EntidadeReceitaDespesa.
 */
@RestController
@RequestMapping("/api")
public class EntidadeReceitaDespesaResource {

    private final Logger log = LoggerFactory.getLogger(EntidadeReceitaDespesaResource.class);

    private static final String ENTITY_NAME = "entidadeReceitaDespesa";

    private final EntidadeReceitaDespesaService entidadeReceitaDespesaService;

    private final UserService userService;

    public EntidadeReceitaDespesaResource(EntidadeReceitaDespesaService entidadeReceitaDespesaService,
                                          UserService userService) {
        this.entidadeReceitaDespesaService = entidadeReceitaDespesaService;
        this.userService = userService;
    }

    /**
     * POST  /entidade-receita-despesas : Create a new entidadeReceitaDespesa.
     *
     * @param entidadeReceitaDespesaDTO the entidadeReceitaDespesaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entidadeReceitaDespesaDTO, or with status 400 (Bad Request) if the entidadeReceitaDespesa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entidade-receita-despesas")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<EntidadeReceitaDespesaDTO> createEntidadeReceitaDespesa(@Valid @RequestBody EntidadeReceitaDespesaDTO entidadeReceitaDespesaDTO) throws URISyntaxException {
        log.debug("REST request to save EntidadeReceitaDespesa : {}", entidadeReceitaDespesaDTO);
        if (entidadeReceitaDespesaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new entidadeReceitaDespesa cannot already have an ID")).body(null);
        }
        EntidadeReceitaDespesaDTO result = entidadeReceitaDespesaService.save(entidadeReceitaDespesaDTO);
        return ResponseEntity.created(new URI("/api/entidade-receita-despesas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entidade-receita-despesas : Updates an existing entidadeReceitaDespesa.
     *
     * @param entidadeReceitaDespesaDTO the entidadeReceitaDespesaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entidadeReceitaDespesaDTO,
     * or with status 400 (Bad Request) if the entidadeReceitaDespesaDTO is not valid,
     * or with status 500 (Internal Server Error) if the entidadeReceitaDespesaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entidade-receita-despesas")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<EntidadeReceitaDespesaDTO> updateEntidadeReceitaDespesa(@Valid @RequestBody EntidadeReceitaDespesaDTO entidadeReceitaDespesaDTO) throws URISyntaxException {
        log.debug("REST request to update EntidadeReceitaDespesa : {}", entidadeReceitaDespesaDTO);
        if (entidadeReceitaDespesaDTO.getId() == null) {
            return createEntidadeReceitaDespesa(entidadeReceitaDespesaDTO);
        }

        if(!userService.isUserEntity(entidadeReceitaDespesaDTO.getUserId())){
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "acaonaopermitidausuario", "Acao nao permitida para este usuario"))
                .body(null);
        }

        EntidadeReceitaDespesaDTO result = entidadeReceitaDespesaService.save(entidadeReceitaDespesaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entidadeReceitaDespesaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entidade-receita-despesas : get all the entidadeReceitaDespesas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of entidadeReceitaDespesas in body
     */
    @GetMapping("/entidade-receita-despesas")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<List<EntidadeReceitaDespesaDTO>> getAllEntidadeReceitaDespesas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of EntidadeReceitaDespesas");
        Page<EntidadeReceitaDespesaDTO> page = entidadeReceitaDespesaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/entidade-receita-despesas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /entidade-receita-despesas/:id : get the "id" entidadeReceitaDespesa.
     *
     * @param id the id of the entidadeReceitaDespesaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entidadeReceitaDespesaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/entidade-receita-despesas/{id}")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<EntidadeReceitaDespesaDTO> getEntidadeReceitaDespesa(@PathVariable Long id) {
        log.debug("REST request to get EntidadeReceitaDespesa : {}", id);
        EntidadeReceitaDespesaDTO entidadeReceitaDespesaDTO = entidadeReceitaDespesaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(entidadeReceitaDespesaDTO));
    }

    /**
     * DELETE  /entidade-receita-despesas/:id : delete the "id" entidadeReceitaDespesa.
     *
     * @param id the id of the entidadeReceitaDespesaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entidade-receita-despesas/{id}")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<Void> deleteEntidadeReceitaDespesa(@PathVariable Long id) {
        log.debug("REST request to delete EntidadeReceitaDespesa : {}", id);
        entidadeReceitaDespesaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
