package br.com.digidev.web.rest;

import br.com.digidev.FinapplicationApp;

import br.com.digidev.domain.TipoConta;
import br.com.digidev.domain.User;
import br.com.digidev.repository.TipoContaRepository;
import br.com.digidev.service.TipoContaService;
import br.com.digidev.service.UserService;
import br.com.digidev.service.dto.TipoContaDTO;
import br.com.digidev.service.mapper.TipoContaMapper;
import br.com.digidev.service.mapper.UserMapper;
import br.com.digidev.web.rest.errors.ExceptionTranslator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TipoContaResource REST controller.
 *
 * @see TipoContaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinapplicationApp.class)
@WithMockUser(username="test",authorities={"ROLE_USER"})
public class TipoContaResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private TipoContaRepository tipoContaRepository;

    @Autowired
    private TipoContaMapper tipoContaMapper;

    @Autowired
    private TipoContaService tipoContaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoContaMockMvc;

    private TipoConta tipoConta;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    private User user;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoContaResource tipoContaResource = new TipoContaResource(tipoContaService, userService);
        this.restTipoContaMockMvc = MockMvcBuilders.standaloneSetup(tipoContaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoConta createEntity(EntityManager em) {
        TipoConta tipoConta = new TipoConta()
            .descricao(DEFAULT_DESCRICAO);
        return tipoConta;
    }

    @Before
    public void initTest() {
        tipoConta = createEntity(em);
    }

    @Before
    public void createUserTest() {
        user = TestUtil.createEntityUser();
        user = userService.createUser(userMapper.userToUserDTO(user));
    }

    @After
    public void finishRemoveUserTest() {
        userService.deleteUser(user.getLogin());
    }

    @Test
    @Transactional
    public void createTipoConta() throws Exception {
        int databaseSizeBeforeCreate = tipoContaRepository.findAll().size();

        // Create the TipoConta
        TipoContaDTO tipoContaDTO = tipoContaMapper.toDto(tipoConta);
        restTipoContaMockMvc.perform(post("/api/tipo-contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoContaDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoConta in the database
        List<TipoConta> tipoContaList = tipoContaRepository.findAll();
        assertThat(tipoContaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoConta testTipoConta = tipoContaList.get(tipoContaList.size() - 1);
        assertThat(testTipoConta.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createTipoContaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoContaRepository.findAll().size();

        // Create the TipoConta with an existing ID
        tipoConta.setId(1L);
        TipoContaDTO tipoContaDTO = tipoContaMapper.toDto(tipoConta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoContaMockMvc.perform(post("/api/tipo-contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoContaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoConta in the database
        List<TipoConta> tipoContaList = tipoContaRepository.findAll();
        assertThat(tipoContaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoContaRepository.findAll().size();
        // set the field null
        tipoConta.setDescricao(null);

        // Create the TipoConta, which fails.
        TipoContaDTO tipoContaDTO = tipoContaMapper.toDto(tipoConta);

        restTipoContaMockMvc.perform(post("/api/tipo-contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoContaDTO)))
            .andExpect(status().isBadRequest());

        List<TipoConta> tipoContaList = tipoContaRepository.findAll();
        assertThat(tipoContaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoContas() throws Exception {
        // Initialize the database
        tipoConta.setUser(user);
        tipoContaRepository.saveAndFlush(tipoConta);

        // Get all the tipoContaList
        restTipoContaMockMvc.perform(get("/api/tipo-contas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoConta.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    public void getTipoConta() throws Exception {
        // Initialize the database
        tipoConta.setUser(user);
        tipoContaRepository.saveAndFlush(tipoConta);

        // Get the tipoConta
        restTipoContaMockMvc.perform(get("/api/tipo-contas/{id}", tipoConta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoConta.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoConta() throws Exception {
        // Get the tipoConta
        restTipoContaMockMvc.perform(get("/api/tipo-contas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoConta() throws Exception {
        // Initialize the database
        tipoContaRepository.saveAndFlush(tipoConta);
        int databaseSizeBeforeUpdate = tipoContaRepository.findAll().size();

        // Update the tipoConta
        TipoConta updatedTipoConta = tipoContaRepository.findOne(tipoConta.getId());
        updatedTipoConta
            .descricao(UPDATED_DESCRICAO);
        TipoContaDTO tipoContaDTO = tipoContaMapper.toDto(updatedTipoConta);
        tipoContaDTO.setUserId(user.getId());

        restTipoContaMockMvc.perform(put("/api/tipo-contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoContaDTO)))
            .andExpect(status().isOk());

        // Validate the TipoConta in the database
        List<TipoConta> tipoContaList = tipoContaRepository.findAll();
        assertThat(tipoContaList).hasSize(databaseSizeBeforeUpdate);
        TipoConta testTipoConta = tipoContaList.get(tipoContaList.size() - 1);
        assertThat(testTipoConta.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoConta() throws Exception {
        int databaseSizeBeforeUpdate = tipoContaRepository.findAll().size();

        // Create the TipoConta
        TipoContaDTO tipoContaDTO = tipoContaMapper.toDto(tipoConta);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipoContaMockMvc.perform(put("/api/tipo-contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoContaDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoConta in the database
        List<TipoConta> tipoContaList = tipoContaRepository.findAll();
        assertThat(tipoContaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTipoConta() throws Exception {
        // Initialize the database
        tipoConta.setUser(user);
        tipoContaRepository.saveAndFlush(tipoConta);
        int databaseSizeBeforeDelete = tipoContaRepository.findAll().size();

        // Get the tipoConta
        restTipoContaMockMvc.perform(delete("/api/tipo-contas/{id}", tipoConta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoConta> tipoContaList = tipoContaRepository.findAll();
        assertThat(tipoContaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoConta.class);
        TipoConta tipoConta1 = new TipoConta();
        tipoConta1.setId(1L);
        TipoConta tipoConta2 = new TipoConta();
        tipoConta2.setId(tipoConta1.getId());
        assertThat(tipoConta1).isEqualTo(tipoConta2);
        tipoConta2.setId(2L);
        assertThat(tipoConta1).isNotEqualTo(tipoConta2);
        tipoConta1.setId(null);
        assertThat(tipoConta1).isNotEqualTo(tipoConta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoContaDTO.class);
        TipoContaDTO tipoContaDTO1 = new TipoContaDTO();
        tipoContaDTO1.setId(1L);
        TipoContaDTO tipoContaDTO2 = new TipoContaDTO();
        assertThat(tipoContaDTO1).isNotEqualTo(tipoContaDTO2);
        tipoContaDTO2.setId(tipoContaDTO1.getId());
        assertThat(tipoContaDTO1).isEqualTo(tipoContaDTO2);
        tipoContaDTO2.setId(2L);
        assertThat(tipoContaDTO1).isNotEqualTo(tipoContaDTO2);
        tipoContaDTO1.setId(null);
        assertThat(tipoContaDTO1).isNotEqualTo(tipoContaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoContaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoContaMapper.fromId(null)).isNull();
    }
}
