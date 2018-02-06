package br.com.digidev.web.rest;

import br.com.digidev.domain.enumeration.Tipo;
import br.com.digidev.security.AuthoritiesConstants;
import br.com.digidev.service.UserService;
import br.com.digidev.service.dto.dashboard.GraficoSimplesDashboardDTO;
import com.codahale.metrics.annotation.Timed;
import br.com.digidev.service.ContaService;
import br.com.digidev.web.rest.util.HeaderUtil;
import br.com.digidev.web.rest.util.PaginationUtil;
import br.com.digidev.service.dto.ContaDTO;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Conta.
 */
@RestController
@RequestMapping("/api")
public class ContaResource {

    private final Logger log = LoggerFactory.getLogger(ContaResource.class);

    private static final String ENTITY_NAME = "conta";

    private final ContaService contaService;

    private final UserService userService;

    public ContaResource(ContaService contaService,
                         UserService userService) {
        this.contaService = contaService;
        this.userService = userService;
    }

    /**
     * POST  /contas : Create a new conta.
     *
     * @param contaDTO the contaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contaDTO, or with status 400 (Bad Request) if the conta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contas")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<ContaDTO> createConta(@Valid @RequestBody ContaDTO contaDTO) throws URISyntaxException {
        log.debug("REST request to save Conta : {}", contaDTO);
        if (contaDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new conta cannot already have an ID")).body(null);
        }
        ContaDTO result = contaService.save(contaDTO);
        return ResponseEntity.created(new URI("/api/contas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contas : Updates an existing conta.
     *
     * @param contaDTO the contaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contaDTO,
     * or with status 400 (Bad Request) if the contaDTO is not valid,
     * or with status 500 (Internal Server Error) if the contaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contas")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<ContaDTO> updateConta(@Valid @RequestBody ContaDTO contaDTO) throws URISyntaxException {
        log.debug("REST request to update Conta : {}", contaDTO);
        if (contaDTO.getId() == null) {
            return createConta(contaDTO);
        }
        if(!userService.isUserEntity(contaDTO.getUserId())){
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "acaonaopermitidausuario", "Acao nao permitida para este usuario"))
                .body(null);
        }
        ContaDTO result = contaService.save(contaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contas : get all the contas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contas in body
     */
    @GetMapping("/contas")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<List<ContaDTO>> getAllContas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Contas");
        Page<ContaDTO> page = contaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contas/:id : get the "id" conta.
     *
     * @param id the id of the contaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/contas/{id}")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<ContaDTO> getConta(@PathVariable Long id) {
        log.debug("REST request to get Conta : {}", id);
        ContaDTO contaDTO = contaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contaDTO));
    }

    /**
     * DELETE  /contas/:id : delete the "id" conta.
     *
     * @param id the id of the contaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contas/{id}")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<Void> deleteConta(@PathVariable Long id) {
        log.debug("REST request to delete Conta : {}", id);
        contaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/contas/dashboard/saldos")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public List<GraficoSimplesDashboardDTO> getAllByDateGroupByCategoria() {
        log.debug("REST request to get saldos by contas");
        return contaService.listaContasSaldos();
    }
}
