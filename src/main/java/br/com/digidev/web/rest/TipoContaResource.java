package br.com.digidev.web.rest;

import br.com.digidev.security.AuthoritiesConstants;
import br.com.digidev.service.UserService;
import com.codahale.metrics.annotation.Timed;
import br.com.digidev.service.TipoContaService;
import br.com.digidev.web.rest.util.HeaderUtil;
import br.com.digidev.web.rest.util.PaginationUtil;
import br.com.digidev.service.dto.TipoContaDTO;
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
 * REST controller for managing TipoConta.
 */
@RestController
@RequestMapping("/api")
public class TipoContaResource {

    private final Logger log = LoggerFactory.getLogger(TipoContaResource.class);

    private static final String ENTITY_NAME = "tipoConta";

    private final TipoContaService tipoContaService;

    private final UserService userService;

    public TipoContaResource(TipoContaService tipoContaService,
                             UserService userService) {
        this.tipoContaService = tipoContaService;
        this.userService = userService;
    }

    /**
     * POST  /tipo-contas : Create a new tipoConta.
     *
     * @param tipoContaDTO the tipoContaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoContaDTO, or with status 400 (Bad Request) if the tipoConta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-contas")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<TipoContaDTO> createTipoConta(@Valid @RequestBody TipoContaDTO tipoContaDTO) throws URISyntaxException {
        log.debug("REST request to save TipoConta : {}", tipoContaDTO);
        if (tipoContaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tipoConta cannot already have an ID")).body(null);
        }
        TipoContaDTO result = tipoContaService.save(tipoContaDTO);
        return ResponseEntity.created(new URI("/api/tipo-contas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-contas : Updates an existing tipoConta.
     *
     * @param tipoContaDTO the tipoContaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoContaDTO,
     * or with status 400 (Bad Request) if the tipoContaDTO is not valid,
     * or with status 500 (Internal Server Error) if the tipoContaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-contas")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<TipoContaDTO> updateTipoConta(@Valid @RequestBody TipoContaDTO tipoContaDTO) throws URISyntaxException {
        log.debug("REST request to update TipoConta : {}", tipoContaDTO);
        if (tipoContaDTO.getId() == null) {
            return createTipoConta(tipoContaDTO);
        }

        if(!userService.isUserEntity(tipoContaDTO.getUserId())){
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "acaonaopermitidausuario", "Acao nao permitida para este usuario"))
                .body(null);
        }

        TipoContaDTO result = tipoContaService.save(tipoContaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoContaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-contas : get all the tipoContas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tipoContas in body
     */
    @GetMapping("/tipo-contas")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<List<TipoContaDTO>> getAllTipoContas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TipoContas");
        Page<TipoContaDTO> page = tipoContaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipo-contas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tipo-contas/:id : get the "id" tipoConta.
     *
     * @param id the id of the tipoContaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoContaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-contas/{id}")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<TipoContaDTO> getTipoConta(@PathVariable Long id) {
        log.debug("REST request to get TipoConta : {}", id);
        TipoContaDTO tipoContaDTO = tipoContaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tipoContaDTO));
    }

    /**
     * DELETE  /tipo-contas/:id : delete the "id" tipoConta.
     *
     * @param id the id of the tipoContaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-contas/{id}")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<Void> deleteTipoConta(@PathVariable Long id) {
        log.debug("REST request to delete TipoConta : {}", id);
        tipoContaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
