package br.com.digidev.web.rest;

import br.com.digidev.FinapplicationApp;

import br.com.digidev.domain.EntidadeReceitaDespesa;
import br.com.digidev.domain.User;
import br.com.digidev.repository.EntidadeReceitaDespesaRepository;
import br.com.digidev.service.EntidadeReceitaDespesaService;
import br.com.digidev.service.UserService;
import br.com.digidev.service.dto.EntidadeReceitaDespesaDTO;
import br.com.digidev.service.mapper.EntidadeReceitaDespesaMapper;
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
 * Test class for the EntidadeReceitaDespesaResource REST controller.
 *
 * @see EntidadeReceitaDespesaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinapplicationApp.class)
@WithMockUser(username="test",authorities={"ROLE_USER"})
public class EntidadeReceitaDespesaResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private EntidadeReceitaDespesaRepository entidadeReceitaDespesaRepository;

    @Autowired
    private EntidadeReceitaDespesaMapper entidadeReceitaDespesaMapper;

    @Autowired
    private EntidadeReceitaDespesaService entidadeReceitaDespesaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEntidadeReceitaDespesaMockMvc;

    private EntidadeReceitaDespesa entidadeReceitaDespesa;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    private User user;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntidadeReceitaDespesaResource entidadeReceitaDespesaResource = new EntidadeReceitaDespesaResource(entidadeReceitaDespesaService, userService);
        this.restEntidadeReceitaDespesaMockMvc = MockMvcBuilders.standaloneSetup(entidadeReceitaDespesaResource)
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
    public static EntidadeReceitaDespesa createEntity(EntityManager em) {
        EntidadeReceitaDespesa entidadeReceitaDespesa = new EntidadeReceitaDespesa()
            .descricao(DEFAULT_DESCRICAO);
        return entidadeReceitaDespesa;
    }

    @Before
    public void initTest() {
        entidadeReceitaDespesa = createEntity(em);
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
    public void createEntidadeReceitaDespesa() throws Exception {
        int databaseSizeBeforeCreate = entidadeReceitaDespesaRepository.findAll().size();

        // Create the EntidadeReceitaDespesa
        EntidadeReceitaDespesaDTO entidadeReceitaDespesaDTO = entidadeReceitaDespesaMapper.toDto(entidadeReceitaDespesa);
        restEntidadeReceitaDespesaMockMvc.perform(post("/api/entidade-receita-despesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entidadeReceitaDespesaDTO)))
            .andExpect(status().isCreated());

        // Validate the EntidadeReceitaDespesa in the database
        List<EntidadeReceitaDespesa> entidadeReceitaDespesaList = entidadeReceitaDespesaRepository.findAll();
        assertThat(entidadeReceitaDespesaList).hasSize(databaseSizeBeforeCreate + 1);
        EntidadeReceitaDespesa testEntidadeReceitaDespesa = entidadeReceitaDespesaList.get(entidadeReceitaDespesaList.size() - 1);
        assertThat(testEntidadeReceitaDespesa.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createEntidadeReceitaDespesaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entidadeReceitaDespesaRepository.findAll().size();

        // Create the EntidadeReceitaDespesa with an existing ID
        entidadeReceitaDespesa.setId(1L);
        EntidadeReceitaDespesaDTO entidadeReceitaDespesaDTO = entidadeReceitaDespesaMapper.toDto(entidadeReceitaDespesa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntidadeReceitaDespesaMockMvc.perform(post("/api/entidade-receita-despesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entidadeReceitaDespesaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EntidadeReceitaDespesa in the database
        List<EntidadeReceitaDespesa> entidadeReceitaDespesaList = entidadeReceitaDespesaRepository.findAll();
        assertThat(entidadeReceitaDespesaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = entidadeReceitaDespesaRepository.findAll().size();
        // set the field null
        entidadeReceitaDespesa.setDescricao(null);

        // Create the EntidadeReceitaDespesa, which fails.
        EntidadeReceitaDespesaDTO entidadeReceitaDespesaDTO = entidadeReceitaDespesaMapper.toDto(entidadeReceitaDespesa);

        restEntidadeReceitaDespesaMockMvc.perform(post("/api/entidade-receita-despesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entidadeReceitaDespesaDTO)))
            .andExpect(status().isBadRequest());

        List<EntidadeReceitaDespesa> entidadeReceitaDespesaList = entidadeReceitaDespesaRepository.findAll();
        assertThat(entidadeReceitaDespesaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEntidadeReceitaDespesas() throws Exception {
        // Initialize the database
        entidadeReceitaDespesa.setUser(user);
        entidadeReceitaDespesaRepository.saveAndFlush(entidadeReceitaDespesa);

        // Get all the entidadeReceitaDespesaList
        restEntidadeReceitaDespesaMockMvc.perform(get("/api/entidade-receita-despesas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entidadeReceitaDespesa.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    public void getEntidadeReceitaDespesa() throws Exception {
        // Initialize the database
        entidadeReceitaDespesa.setUser(user);
        entidadeReceitaDespesaRepository.saveAndFlush(entidadeReceitaDespesa);

        // Get the entidadeReceitaDespesa
        restEntidadeReceitaDespesaMockMvc.perform(get("/api/entidade-receita-despesas/{id}", entidadeReceitaDespesa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entidadeReceitaDespesa.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEntidadeReceitaDespesa() throws Exception {
        // Get the entidadeReceitaDespesa
        restEntidadeReceitaDespesaMockMvc.perform(get("/api/entidade-receita-despesas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntidadeReceitaDespesa() throws Exception {
        // Initialize the database
        entidadeReceitaDespesaRepository.saveAndFlush(entidadeReceitaDespesa);
        int databaseSizeBeforeUpdate = entidadeReceitaDespesaRepository.findAll().size();

        // Update the entidadeReceitaDespesa
        EntidadeReceitaDespesa updatedEntidadeReceitaDespesa = entidadeReceitaDespesaRepository.findOne(entidadeReceitaDespesa.getId());
        updatedEntidadeReceitaDespesa
            .descricao(UPDATED_DESCRICAO);
        EntidadeReceitaDespesaDTO entidadeReceitaDespesaDTO = entidadeReceitaDespesaMapper.toDto(updatedEntidadeReceitaDespesa);
        entidadeReceitaDespesaDTO.setUserId(user.getId());

        restEntidadeReceitaDespesaMockMvc.perform(put("/api/entidade-receita-despesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entidadeReceitaDespesaDTO)))
            .andExpect(status().isOk());

        // Validate the EntidadeReceitaDespesa in the database
        List<EntidadeReceitaDespesa> entidadeReceitaDespesaList = entidadeReceitaDespesaRepository.findAll();
        assertThat(entidadeReceitaDespesaList).hasSize(databaseSizeBeforeUpdate);
        EntidadeReceitaDespesa testEntidadeReceitaDespesa = entidadeReceitaDespesaList.get(entidadeReceitaDespesaList.size() - 1);
        assertThat(testEntidadeReceitaDespesa.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingEntidadeReceitaDespesa() throws Exception {
        int databaseSizeBeforeUpdate = entidadeReceitaDespesaRepository.findAll().size();

        // Create the EntidadeReceitaDespesa
        EntidadeReceitaDespesaDTO entidadeReceitaDespesaDTO = entidadeReceitaDespesaMapper.toDto(entidadeReceitaDespesa);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEntidadeReceitaDespesaMockMvc.perform(put("/api/entidade-receita-despesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entidadeReceitaDespesaDTO)))
            .andExpect(status().isCreated());

        // Validate the EntidadeReceitaDespesa in the database
        List<EntidadeReceitaDespesa> entidadeReceitaDespesaList = entidadeReceitaDespesaRepository.findAll();
        assertThat(entidadeReceitaDespesaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEntidadeReceitaDespesa() throws Exception {
        // Initialize the database
        entidadeReceitaDespesa.setUser(user);
        entidadeReceitaDespesaRepository.saveAndFlush(entidadeReceitaDespesa);
        int databaseSizeBeforeDelete = entidadeReceitaDespesaRepository.findAll().size();

        // Get the entidadeReceitaDespesa
        restEntidadeReceitaDespesaMockMvc.perform(delete("/api/entidade-receita-despesas/{id}", entidadeReceitaDespesa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EntidadeReceitaDespesa> entidadeReceitaDespesaList = entidadeReceitaDespesaRepository.findAll();
        assertThat(entidadeReceitaDespesaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntidadeReceitaDespesa.class);
        EntidadeReceitaDespesa entidadeReceitaDespesa1 = new EntidadeReceitaDespesa();
        entidadeReceitaDespesa1.setId(1L);
        EntidadeReceitaDespesa entidadeReceitaDespesa2 = new EntidadeReceitaDespesa();
        entidadeReceitaDespesa2.setId(entidadeReceitaDespesa1.getId());
        assertThat(entidadeReceitaDespesa1).isEqualTo(entidadeReceitaDespesa2);
        entidadeReceitaDespesa2.setId(2L);
        assertThat(entidadeReceitaDespesa1).isNotEqualTo(entidadeReceitaDespesa2);
        entidadeReceitaDespesa1.setId(null);
        assertThat(entidadeReceitaDespesa1).isNotEqualTo(entidadeReceitaDespesa2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntidadeReceitaDespesaDTO.class);
        EntidadeReceitaDespesaDTO entidadeReceitaDespesaDTO1 = new EntidadeReceitaDespesaDTO();
        entidadeReceitaDespesaDTO1.setId(1L);
        EntidadeReceitaDespesaDTO entidadeReceitaDespesaDTO2 = new EntidadeReceitaDespesaDTO();
        assertThat(entidadeReceitaDespesaDTO1).isNotEqualTo(entidadeReceitaDespesaDTO2);
        entidadeReceitaDespesaDTO2.setId(entidadeReceitaDespesaDTO1.getId());
        assertThat(entidadeReceitaDespesaDTO1).isEqualTo(entidadeReceitaDespesaDTO2);
        entidadeReceitaDespesaDTO2.setId(2L);
        assertThat(entidadeReceitaDespesaDTO1).isNotEqualTo(entidadeReceitaDespesaDTO2);
        entidadeReceitaDespesaDTO1.setId(null);
        assertThat(entidadeReceitaDespesaDTO1).isNotEqualTo(entidadeReceitaDespesaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(entidadeReceitaDespesaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(entidadeReceitaDespesaMapper.fromId(null)).isNull();
    }
}
