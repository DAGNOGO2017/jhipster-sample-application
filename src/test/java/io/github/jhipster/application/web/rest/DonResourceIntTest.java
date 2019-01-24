package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Don;
import io.github.jhipster.application.repository.DonRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DonResource REST controller.
 *
 * @see DonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class DonResourceIntTest {

    private static final Integer DEFAULT_ID_DON = 1;
    private static final Integer UPDATED_ID_DON = 2;

    private static final String DEFAULT_NATURE_DON = "AAAAAAAAAA";
    private static final String UPDATED_NATURE_DON = "BBBBBBBBBB";

    @Autowired
    private DonRepository donRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restDonMockMvc;

    private Don don;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DonResource donResource = new DonResource(donRepository);
        this.restDonMockMvc = MockMvcBuilders.standaloneSetup(donResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Don createEntity(EntityManager em) {
        Don don = new Don()
            .idDon(DEFAULT_ID_DON)
            .natureDon(DEFAULT_NATURE_DON);
        return don;
    }

    @Before
    public void initTest() {
        don = createEntity(em);
    }

    @Test
    @Transactional
    public void createDon() throws Exception {
        int databaseSizeBeforeCreate = donRepository.findAll().size();

        // Create the Don
        restDonMockMvc.perform(post("/api/dons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(don)))
            .andExpect(status().isCreated());

        // Validate the Don in the database
        List<Don> donList = donRepository.findAll();
        assertThat(donList).hasSize(databaseSizeBeforeCreate + 1);
        Don testDon = donList.get(donList.size() - 1);
        assertThat(testDon.getIdDon()).isEqualTo(DEFAULT_ID_DON);
        assertThat(testDon.getNatureDon()).isEqualTo(DEFAULT_NATURE_DON);
    }

    @Test
    @Transactional
    public void createDonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = donRepository.findAll().size();

        // Create the Don with an existing ID
        don.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDonMockMvc.perform(post("/api/dons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(don)))
            .andExpect(status().isBadRequest());

        // Validate the Don in the database
        List<Don> donList = donRepository.findAll();
        assertThat(donList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDons() throws Exception {
        // Initialize the database
        donRepository.saveAndFlush(don);

        // Get all the donList
        restDonMockMvc.perform(get("/api/dons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(don.getId().intValue())))
            .andExpect(jsonPath("$.[*].idDon").value(hasItem(DEFAULT_ID_DON)))
            .andExpect(jsonPath("$.[*].natureDon").value(hasItem(DEFAULT_NATURE_DON.toString())));
    }
    
    @Test
    @Transactional
    public void getDon() throws Exception {
        // Initialize the database
        donRepository.saveAndFlush(don);

        // Get the don
        restDonMockMvc.perform(get("/api/dons/{id}", don.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(don.getId().intValue()))
            .andExpect(jsonPath("$.idDon").value(DEFAULT_ID_DON))
            .andExpect(jsonPath("$.natureDon").value(DEFAULT_NATURE_DON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDon() throws Exception {
        // Get the don
        restDonMockMvc.perform(get("/api/dons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDon() throws Exception {
        // Initialize the database
        donRepository.saveAndFlush(don);

        int databaseSizeBeforeUpdate = donRepository.findAll().size();

        // Update the don
        Don updatedDon = donRepository.findById(don.getId()).get();
        // Disconnect from session so that the updates on updatedDon are not directly saved in db
        em.detach(updatedDon);
        updatedDon
            .idDon(UPDATED_ID_DON)
            .natureDon(UPDATED_NATURE_DON);

        restDonMockMvc.perform(put("/api/dons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDon)))
            .andExpect(status().isOk());

        // Validate the Don in the database
        List<Don> donList = donRepository.findAll();
        assertThat(donList).hasSize(databaseSizeBeforeUpdate);
        Don testDon = donList.get(donList.size() - 1);
        assertThat(testDon.getIdDon()).isEqualTo(UPDATED_ID_DON);
        assertThat(testDon.getNatureDon()).isEqualTo(UPDATED_NATURE_DON);
    }

    @Test
    @Transactional
    public void updateNonExistingDon() throws Exception {
        int databaseSizeBeforeUpdate = donRepository.findAll().size();

        // Create the Don

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonMockMvc.perform(put("/api/dons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(don)))
            .andExpect(status().isBadRequest());

        // Validate the Don in the database
        List<Don> donList = donRepository.findAll();
        assertThat(donList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDon() throws Exception {
        // Initialize the database
        donRepository.saveAndFlush(don);

        int databaseSizeBeforeDelete = donRepository.findAll().size();

        // Get the don
        restDonMockMvc.perform(delete("/api/dons/{id}", don.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Don> donList = donRepository.findAll();
        assertThat(donList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Don.class);
        Don don1 = new Don();
        don1.setId(1L);
        Don don2 = new Don();
        don2.setId(don1.getId());
        assertThat(don1).isEqualTo(don2);
        don2.setId(2L);
        assertThat(don1).isNotEqualTo(don2);
        don1.setId(null);
        assertThat(don1).isNotEqualTo(don2);
    }
}
