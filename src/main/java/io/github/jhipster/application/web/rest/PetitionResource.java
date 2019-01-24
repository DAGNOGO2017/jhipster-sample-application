package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Petition;
import io.github.jhipster.application.service.PetitionService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Petition.
 */
@RestController
@RequestMapping("/api")
public class PetitionResource {

    private final Logger log = LoggerFactory.getLogger(PetitionResource.class);

    private static final String ENTITY_NAME = "petition";

    private final PetitionService petitionService;

    public PetitionResource(PetitionService petitionService) {
        this.petitionService = petitionService;
    }

    /**
     * POST  /petitions : Create a new petition.
     *
     * @param petition the petition to create
     * @return the ResponseEntity with status 201 (Created) and with body the new petition, or with status 400 (Bad Request) if the petition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/petitions")
    @Timed
    public ResponseEntity<Petition> createPetition(@RequestBody Petition petition) throws URISyntaxException {
        log.debug("REST request to save Petition : {}", petition);
        if (petition.getId() != null) {
            throw new BadRequestAlertException("A new petition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Petition result = petitionService.save(petition);
        return ResponseEntity.created(new URI("/api/petitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /petitions : Updates an existing petition.
     *
     * @param petition the petition to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated petition,
     * or with status 400 (Bad Request) if the petition is not valid,
     * or with status 500 (Internal Server Error) if the petition couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/petitions")
    @Timed
    public ResponseEntity<Petition> updatePetition(@RequestBody Petition petition) throws URISyntaxException {
        log.debug("REST request to update Petition : {}", petition);
        if (petition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Petition result = petitionService.save(petition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, petition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /petitions : get all the petitions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of petitions in body
     */
    @GetMapping("/petitions")
    @Timed
    public List<Petition> getAllPetitions() {
        log.debug("REST request to get all Petitions");
        return petitionService.findAll();
    }

    /**
     * GET  /petitions/:id : get the "id" petition.
     *
     * @param id the id of the petition to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the petition, or with status 404 (Not Found)
     */
    @GetMapping("/petitions/{id}")
    @Timed
    public ResponseEntity<Petition> getPetition(@PathVariable Long id) {
        log.debug("REST request to get Petition : {}", id);
        Optional<Petition> petition = petitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(petition);
    }

    /**
     * DELETE  /petitions/:id : delete the "id" petition.
     *
     * @param id the id of the petition to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/petitions/{id}")
    @Timed
    public ResponseEntity<Void> deletePetition(@PathVariable Long id) {
        log.debug("REST request to delete Petition : {}", id);
        petitionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
