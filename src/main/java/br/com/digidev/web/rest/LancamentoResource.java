package br.com.digidev.web.rest;

import br.com.digidev.domain.Lancamento;
import br.com.digidev.domain.enumeration.Tipo;
import br.com.digidev.security.AuthoritiesConstants;
import br.com.digidev.service.UserService;
import br.com.digidev.service.dto.dashboard.GraficoSimplesDashboardDTO;
import br.com.digidev.service.dto.dashboard.GraficoSimplesDashboardTipoEnumDTO;
import com.codahale.metrics.annotation.Timed;
import br.com.digidev.service.LancamentoService;
import br.com.digidev.web.rest.util.HeaderUtil;
import br.com.digidev.web.rest.util.PaginationUtil;
import br.com.digidev.service.dto.LancamentoDTO;
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
 * REST controller for managing Lancamento.
 */
@RestController
@RequestMapping("/api")
public class LancamentoResource {

    private final Logger log = LoggerFactory.getLogger(LancamentoResource.class);

    private static final String ENTITY_NAME = "lancamento";

    private final LancamentoService lancamentoService;

    private final UserService userService;

    public LancamentoResource(LancamentoService lancamentoService,
                              UserService userService) {
        this.lancamentoService = lancamentoService;
        this.userService = userService;
    }

    /**
     * POST  /lancamentos : Create a new lancamento.
     *
     * @param lancamentoDTO the lancamentoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lancamentoDTO, or with status 400 (Bad Request) if the lancamento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lancamentos")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<LancamentoDTO> createLancamento(@Valid @RequestBody LancamentoDTO lancamentoDTO) throws URISyntaxException {
        log.debug("REST request to save Lancamento : {}", lancamentoDTO);
        if (lancamentoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new lancamento cannot already have an ID")).body(null);
        }
        LancamentoDTO result = lancamentoService.save(lancamentoDTO);
        return ResponseEntity.created(new URI("/api/lancamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lancamentos : Updates an existing lancamento.
     *
     * @param lancamentoDTO the lancamentoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lancamentoDTO,
     * or with status 400 (Bad Request) if the lancamentoDTO is not valid,
     * or with status 500 (Internal Server Error) if the lancamentoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lancamentos")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<LancamentoDTO> updateLancamento(@Valid @RequestBody LancamentoDTO lancamentoDTO) throws URISyntaxException {
        log.debug("REST request to update Lancamento : {}", lancamentoDTO);
        if (lancamentoDTO.getId() == null) {
            return createLancamento(lancamentoDTO);
        }

        if(!userService.isUserEntity(lancamentoDTO.getUserId())){
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "acaonaopermitidausuario", "Acao nao permitida para este usuario"))
                .body(null);
        }

        LancamentoDTO result = lancamentoService.save(lancamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lancamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lancamentos : get all the lancamentos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lancamentos in body
     */
    @GetMapping("/lancamentos")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<List<LancamentoDTO>> getAllLancamentos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Lancamentos");
        Page<LancamentoDTO> page = lancamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lancamentos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /lancamentos/:id : get the "id" lancamento.
     *
     * @param id the id of the lancamentoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lancamentoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lancamentos/{id}")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<LancamentoDTO> getLancamento(@PathVariable Long id) {
        log.debug("REST request to get Lancamento : {}", id);
        LancamentoDTO lancamentoDTO = lancamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lancamentoDTO));
    }

    /**
     * DELETE  /lancamentos/:id : delete the "id" lancamento.
     *
     * @param id the id of the lancamentoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lancamentos/{id}")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<Void> deleteLancamento(@PathVariable Long id) {
        log.debug("REST request to delete Lancamento : {}", id);
        if(lancamentoService.isLancamentosVinculados(id)){
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "existlancamentovinculado", "Existe lancamentos vinculados"))
                .body(null);
        }
        lancamentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/lancamentos-dashboard-by-tipo")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<List<LancamentoDTO>> getLancamentosByTipoData(@RequestParam(value = "tipo") Tipo tipo,
                                                                        @RequestParam(value = "dataSelecionada") LocalDate dataSelecionada) {
        log.debug("REST request to get Lancamentos");
        List<LancamentoDTO> lancamentos = lancamentoService.retornaLancamentosPorTipoData(tipo, dataSelecionada);
        return new ResponseEntity<>(lancamentos, HttpStatus.OK);
    }

    @GetMapping("/lancamentos/dashboard/grupo-mes/categoria")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public List<GraficoSimplesDashboardDTO> getAllByDateGroupByCategoria(@RequestParam(value = "tipo") Tipo tipo,
                                                                         @RequestParam(value = "dataSelecionada") LocalDate dataSelecionada) {
        log.debug("REST request to get lancamentos group by categoria");
        return lancamentoService.getAllByDateGroupByCategoria(dataSelecionada, tipo);
    }

    @GetMapping("/lancamentos/dashboard/grupo-mes/tipo")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public List<GraficoSimplesDashboardTipoEnumDTO> getAllByDateGroupByTipo(@RequestParam(value = "dataSelecionada") LocalDate dataSelecionada) {
        log.debug("REST request to get lancamentos group by tipo");
        return lancamentoService.getAllByDateGroupByTipo(dataSelecionada);
    }
}
