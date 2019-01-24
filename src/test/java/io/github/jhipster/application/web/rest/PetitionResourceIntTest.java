package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Petition;
import io.github.jhipster.application.repository.PetitionRepository;
import io.github.jhipster.application.service.PetitionService;
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
 * Test class for the PetitionResource REST controller.
 *
 * @see PetitionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class PetitionResourceIntTest {

    private static final Integer DEFAULT_ID_PETITION = 1;
    private static final Integer UPDATED_ID_PETITION = 2;

    private static final String DEFAULT_LIBELEPETITION = "AAAAAAAAAA";
    private static final String UPDATED_LIBELEPETITION = "BBBBBBBBBB";

    @Autowired
    private PetitionRepository petitionRepository;

    @Autowired
    private PetitionService petitionService;

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

    private MockMvc restPetitionMockMvc;

    private Petition petition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PetitionResource petitionResource = new PetitionResource(petitionService);
        this.restPetitionMockMvc = MockMvcBuilders.standaloneSetup(petitionResource)
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
    public static Petition createEntity(EntityManager em) {
        Petition petition = new Petition()
            .idPetition(DEFAULT_ID_PETITION)
            .libelepetition(DEFAULT_LIBELEPETITION);
        return petition;
    }

    @Before
    public void initTest() {
        petition = createEntity(em);
    }

    @Test
    @Transactional
    public void createPetition() throws Exception {
        int databaseSizeBeforeCreate = petitionRepository.findAll().size();

        // Create the Petition
        restPetitionMockMvc.perform(post("/api/petitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(petition)))
            .andExpect(status().isCreated());

        // Validate the Petition in the database
        List<Petition> petitionList = petitionRepository.findAll();
        assertThat(petitionList).hasSize(databaseSizeBeforeCreate + 1);
        Petition testPetition = petitionList.get(petitionList.size() - 1);
        assertThat(testPetition.getIdPetition()).isEqualTo(DEFAULT_ID_PETITION);
        assertThat(testPetition.getLibelepetition()).isEqualTo(DEFAULT_LIBELEPETITION);
    }

    @Test
    @Transactional
    public void createPetitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = petitionRepository.findAll().size();

        // Create the Petition with an existing ID
        petition.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPetitionMockMvc.perform(post("/api/petitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(petition)))
            .andExpect(status().isBadRequest());

        // Validate the Petition in the database
        List<Petition> petitionList = petitionRepository.findAll();
        assertThat(petitionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPetitions() throws Exception {
        // Initialize the database
        petitionRepository.saveAndFlush(petition);

        // Get all the petitionList
        restPetitionMockMvc.perform(get("/api/petitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(petition.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPetition").value(hasItem(DEFAULT_ID_PETITION)))
            .andExpect(jsonPath("$.[*].libelepetition").value(hasItem(DEFAULT_LIBELEPETITION.toString())));
    }
    
    @Test
    @Transactional
    public void getPetition() throws Exception {
        // Initialize the database
        petitionRepository.saveAndFlush(petition);

        // Get the petition
        restPetitionMockMvc.perform(get("/api/petitions/{id}", petition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(petition.getId().intValue()))
            .andExpect(jsonPath("$.idPetition").value(DEFAULT_ID_PETITION))
            .andExpect(jsonPath("$.libelepetition").value(DEFAULT_LIBELEPETITION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPetition() throws Exception {
        // Get the petition
        restPetitionMockMvc.perform(get("/api/petitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePetition() throws Exception {
        // Initialize the database
        petitionService.save(petition);

        int databaseSizeBeforeUpdate = petitionRepository.findAll().size();

        // Update the petition
        Petition updatedPetition = petitionRepository.findById(petition.getId()).get();
        // Disconnect from session so that the updates on updatedPetition are not directly saved in db
        em.detach(updatedPetition);
        updatedPetition
            .idPetition(UPDATED_ID_PETITION)
            .libelepetition(UPDATED_LIBELEPETITION);

        restPetitionMockMvc.perform(put("/api/petitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPetition)))
            .andExpect(status().isOk());

        // Validate the Petition in the database
        List<Petition> petitionList = petitionRepository.findAll();
        assertThat(petitionList).hasSize(databaseSizeBeforeUpdate);
        Petition testPetition = petitionList.get(petitionList.size() - 1);
        assertThat(testPetition.getIdPetition()).isEqualTo(UPDATED_ID_PETITION);
        assertThat(testPetition.getLibelepetition()).isEqualTo(UPDATED_LIBELEPETITION);
    }

    @Test
    @Transactional
    public void updateNonExistingPetition() throws Exception {
        int databaseSizeBeforeUpdate = petitionRepository.findAll().size();

        // Create the Petition

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPetitionMockMvc.perform(put("/api/petitions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(petition)))
            .andExpect(status().isBadRequest());

        // Validate the Petition in the database
        List<Petition> petitionList = petitionRepository.findAll();
        assertThat(petitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePetition() throws Exception {
        // Initialize the database
        petitionService.save(petition);

        int databaseSizeBeforeDelete = petitionRepository.findAll().size();

        // Get the petition
        restPetitionMockMvc.perform(delete("/api/petitions/{id}", petition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Petition> petitionList = petitionRepository.findAll();
        assertThat(petitionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Petition.class);
        Petition petition1 = new Petition();
        petition1.setId(1L);
        Petition petition2 = new Petition();
        petition2.setId(petition1.getId());
        assertThat(petition1).isEqualTo(petition2);
        petition2.setId(2L);
        assertThat(petition1).isNotEqualTo(petition2);
        petition1.setId(null);
        assertThat(petition1).isNotEqualTo(petition2);
    }
}
