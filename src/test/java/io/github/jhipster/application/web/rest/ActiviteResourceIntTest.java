package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Activite;
import io.github.jhipster.application.repository.ActiviteRepository;
import io.github.jhipster.application.service.ActiviteService;
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
 * Test class for the ActiviteResource REST controller.
 *
 * @see ActiviteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class ActiviteResourceIntTest {

    private static final Integer DEFAULT_ID_ACTIVITE = 1;
    private static final Integer UPDATED_ID_ACTIVITE = 2;

    private static final String DEFAULT_NOM_ACTIVITE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_ACTIVITE = "BBBBBBBBBB";

    @Autowired
    private ActiviteRepository activiteRepository;

    @Autowired
    private ActiviteService activiteService;

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

    private MockMvc restActiviteMockMvc;

    private Activite activite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActiviteResource activiteResource = new ActiviteResource(activiteService);
        this.restActiviteMockMvc = MockMvcBuilders.standaloneSetup(activiteResource)
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
    public static Activite createEntity(EntityManager em) {
        Activite activite = new Activite()
            .idActivite(DEFAULT_ID_ACTIVITE)
            .nomActivite(DEFAULT_NOM_ACTIVITE);
        return activite;
    }

    @Before
    public void initTest() {
        activite = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivite() throws Exception {
        int databaseSizeBeforeCreate = activiteRepository.findAll().size();

        // Create the Activite
        restActiviteMockMvc.perform(post("/api/activites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activite)))
            .andExpect(status().isCreated());

        // Validate the Activite in the database
        List<Activite> activiteList = activiteRepository.findAll();
        assertThat(activiteList).hasSize(databaseSizeBeforeCreate + 1);
        Activite testActivite = activiteList.get(activiteList.size() - 1);
        assertThat(testActivite.getIdActivite()).isEqualTo(DEFAULT_ID_ACTIVITE);
        assertThat(testActivite.getNomActivite()).isEqualTo(DEFAULT_NOM_ACTIVITE);
    }

    @Test
    @Transactional
    public void createActiviteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activiteRepository.findAll().size();

        // Create the Activite with an existing ID
        activite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActiviteMockMvc.perform(post("/api/activites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activite)))
            .andExpect(status().isBadRequest());

        // Validate the Activite in the database
        List<Activite> activiteList = activiteRepository.findAll();
        assertThat(activiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomActiviteIsRequired() throws Exception {
        int databaseSizeBeforeTest = activiteRepository.findAll().size();
        // set the field null
        activite.setNomActivite(null);

        // Create the Activite, which fails.

        restActiviteMockMvc.perform(post("/api/activites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activite)))
            .andExpect(status().isBadRequest());

        List<Activite> activiteList = activiteRepository.findAll();
        assertThat(activiteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActivites() throws Exception {
        // Initialize the database
        activiteRepository.saveAndFlush(activite);

        // Get all the activiteList
        restActiviteMockMvc.perform(get("/api/activites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activite.getId().intValue())))
            .andExpect(jsonPath("$.[*].idActivite").value(hasItem(DEFAULT_ID_ACTIVITE)))
            .andExpect(jsonPath("$.[*].nomActivite").value(hasItem(DEFAULT_NOM_ACTIVITE.toString())));
    }
    
    @Test
    @Transactional
    public void getActivite() throws Exception {
        // Initialize the database
        activiteRepository.saveAndFlush(activite);

        // Get the activite
        restActiviteMockMvc.perform(get("/api/activites/{id}", activite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activite.getId().intValue()))
            .andExpect(jsonPath("$.idActivite").value(DEFAULT_ID_ACTIVITE))
            .andExpect(jsonPath("$.nomActivite").value(DEFAULT_NOM_ACTIVITE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingActivite() throws Exception {
        // Get the activite
        restActiviteMockMvc.perform(get("/api/activites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivite() throws Exception {
        // Initialize the database
        activiteService.save(activite);

        int databaseSizeBeforeUpdate = activiteRepository.findAll().size();

        // Update the activite
        Activite updatedActivite = activiteRepository.findById(activite.getId()).get();
        // Disconnect from session so that the updates on updatedActivite are not directly saved in db
        em.detach(updatedActivite);
        updatedActivite
            .idActivite(UPDATED_ID_ACTIVITE)
            .nomActivite(UPDATED_NOM_ACTIVITE);

        restActiviteMockMvc.perform(put("/api/activites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActivite)))
            .andExpect(status().isOk());

        // Validate the Activite in the database
        List<Activite> activiteList = activiteRepository.findAll();
        assertThat(activiteList).hasSize(databaseSizeBeforeUpdate);
        Activite testActivite = activiteList.get(activiteList.size() - 1);
        assertThat(testActivite.getIdActivite()).isEqualTo(UPDATED_ID_ACTIVITE);
        assertThat(testActivite.getNomActivite()).isEqualTo(UPDATED_NOM_ACTIVITE);
    }

    @Test
    @Transactional
    public void updateNonExistingActivite() throws Exception {
        int databaseSizeBeforeUpdate = activiteRepository.findAll().size();

        // Create the Activite

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActiviteMockMvc.perform(put("/api/activites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activite)))
            .andExpect(status().isBadRequest());

        // Validate the Activite in the database
        List<Activite> activiteList = activiteRepository.findAll();
        assertThat(activiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActivite() throws Exception {
        // Initialize the database
        activiteService.save(activite);

        int databaseSizeBeforeDelete = activiteRepository.findAll().size();

        // Get the activite
        restActiviteMockMvc.perform(delete("/api/activites/{id}", activite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Activite> activiteList = activiteRepository.findAll();
        assertThat(activiteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Activite.class);
        Activite activite1 = new Activite();
        activite1.setId(1L);
        Activite activite2 = new Activite();
        activite2.setId(activite1.getId());
        assertThat(activite1).isEqualTo(activite2);
        activite2.setId(2L);
        assertThat(activite1).isNotEqualTo(activite2);
        activite1.setId(null);
        assertThat(activite1).isNotEqualTo(activite2);
    }
}
