package br.com.digidev.web.rest;

import br.com.digidev.FinapplicationApp;
import br.com.digidev.domain.Conta;
import br.com.digidev.domain.User;
import br.com.digidev.repository.ContaRepository;
import br.com.digidev.service.ContaService;
import br.com.digidev.service.UserService;
import br.com.digidev.service.dto.ContaDTO;
import br.com.digidev.service.mapper.ContaMapper;
import br.com.digidev.service.mapper.UserMapper;
import br.com.digidev.web.rest.errors.ExceptionTranslator;
import org.apache.commons.lang3.RandomStringUtils;
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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ContaResource REST controller.
 *
 * @see ContaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinapplicationApp.class)
@WithMockUser(username="test",authorities={"ROLE_USER"})
public class ContaResourceIntTest {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SALDO_INICIAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALDO_INICIAL = new BigDecimal(2);

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ContaMapper contaMapper;

    @Autowired
    private ContaService contaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContaMockMvc;

    private Conta conta;

    private User user;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContaResource contaResource = new ContaResource(contaService, userService);
        this.restContaMockMvc = MockMvcBuilders.standaloneSetup(contaResource)
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
    public static Conta createEntity(EntityManager em) {
        Conta conta = new Conta()
            .descricao(DEFAULT_DESCRICAO)
            .saldoInicial(DEFAULT_SALDO_INICIAL);
        return conta;
    }

    @Before
    public void initTest() {
        conta = createEntity(em);
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
    public void createConta() throws Exception {
        int databaseSizeBeforeCreate = contaRepository.findAll().size();

        // Create the Conta
        ContaDTO contaDTO = contaMapper.toDto(conta);
        restContaMockMvc.perform(post("/api/contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isCreated());

        // Validate the Conta in the database
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeCreate + 1);
        Conta testConta = contaList.get(contaList.size() - 1);
        assertThat(testConta.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testConta.getSaldoInicial()).isEqualTo(DEFAULT_SALDO_INICIAL);
    }

    @Test
    @Transactional
    public void createContaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contaRepository.findAll().size();

        // Create the Conta with an existing ID
        conta.setId(1L);
        ContaDTO contaDTO = contaMapper.toDto(conta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContaMockMvc.perform(post("/api/contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Conta in the database
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = contaRepository.findAll().size();
        // set the field null
        conta.setDescricao(null);

        // Create the Conta, which fails.
        ContaDTO contaDTO = contaMapper.toDto(conta);

        restContaMockMvc.perform(post("/api/contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isBadRequest());

        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSaldoInicialIsRequired() throws Exception {
        int databaseSizeBeforeTest = contaRepository.findAll().size();
        // set the field null
        conta.setSaldoInicial(null);

        // Create the Conta, which fails.
        ContaDTO contaDTO = contaMapper.toDto(conta);

        restContaMockMvc.perform(post("/api/contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isBadRequest());

        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContas() throws Exception {
        // Initialize the database
        conta.setUser(user);
        contaRepository.saveAndFlush(conta);

        // Get all the contaList
        restContaMockMvc.perform(get("/api/contas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conta.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].saldoInicial").value(hasItem(DEFAULT_SALDO_INICIAL.intValue())));
    }

    @Test
    @Transactional
    public void getConta() throws Exception {
        // Initialize the database
        conta.setUser(user);
        contaRepository.saveAndFlush(conta);

        // Get the conta
        restContaMockMvc.perform(get("/api/contas/{id}", conta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(conta.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.saldoInicial").value(DEFAULT_SALDO_INICIAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingConta() throws Exception {
        // Get the conta
        restContaMockMvc.perform(get("/api/contas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConta() throws Exception {
        // Initialize the database
        contaRepository.saveAndFlush(conta);
        int databaseSizeBeforeUpdate = contaRepository.findAll().size();

        // Update the conta
        Conta updatedConta = contaRepository.findOne(conta.getId());
        updatedConta
            .descricao(UPDATED_DESCRICAO)
            .saldoInicial(UPDATED_SALDO_INICIAL);
        ContaDTO contaDTO = contaMapper.toDto(updatedConta);
        contaDTO.setUserId(user.getId());

        restContaMockMvc.perform(put("/api/contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isOk());

        // Validate the Conta in the database
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeUpdate);
        Conta testConta = contaList.get(contaList.size() - 1);
        assertThat(testConta.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testConta.getSaldoInicial()).isEqualTo(UPDATED_SALDO_INICIAL);
    }

    @Test
    @Transactional
    public void updateNonExistingConta() throws Exception {
        int databaseSizeBeforeUpdate = contaRepository.findAll().size();

        // Create the Conta
        ContaDTO contaDTO = contaMapper.toDto(conta);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContaMockMvc.perform(put("/api/contas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contaDTO)))
            .andExpect(status().isCreated());

        // Validate the Conta in the database
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConta() throws Exception {
        // Initialize the database
        conta.setUser(user);
        contaRepository.saveAndFlush(conta);
        int databaseSizeBeforeDelete = contaRepository.findAll().size();

        // Get the conta
        restContaMockMvc.perform(delete("/api/contas/{id}", conta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Conta> contaList = contaRepository.findAll();
        assertThat(contaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Conta.class);
        Conta conta1 = new Conta();
        conta1.setId(1L);
        Conta conta2 = new Conta();
        conta2.setId(conta1.getId());
        assertThat(conta1).isEqualTo(conta2);
        conta2.setId(2L);
        assertThat(conta1).isNotEqualTo(conta2);
        conta1.setId(null);
        assertThat(conta1).isNotEqualTo(conta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContaDTO.class);
        ContaDTO contaDTO1 = new ContaDTO();
        contaDTO1.setId(1L);
        ContaDTO contaDTO2 = new ContaDTO();
        assertThat(contaDTO1).isNotEqualTo(contaDTO2);
        contaDTO2.setId(contaDTO1.getId());
        assertThat(contaDTO1).isEqualTo(contaDTO2);
        contaDTO2.setId(2L);
        assertThat(contaDTO1).isNotEqualTo(contaDTO2);
        contaDTO1.setId(null);
        assertThat(contaDTO1).isNotEqualTo(contaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contaMapper.fromId(null)).isNull();
    }
}
