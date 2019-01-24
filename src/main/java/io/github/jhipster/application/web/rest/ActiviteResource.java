package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Activite;
import io.github.jhipster.application.service.ActiviteService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Activite.
 */
@RestController
@RequestMapping("/api")
public class ActiviteResource {

    private final Logger log = LoggerFactory.getLogger(ActiviteResource.class);

    private static final String ENTITY_NAME = "activite";

    private final ActiviteService activiteService;

    public ActiviteResource(ActiviteService activiteService) {
        this.activiteService = activiteService;
    }

    /**
     * POST  /activites : Create a new activite.
     *
     * @param activite the activite to create
     * @return the ResponseEntity with status 201 (Created) and with body the new activite, or with status 400 (Bad Request) if the activite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/activites")
    @Timed
    public ResponseEntity<Activite> createActivite(@Valid @RequestBody Activite activite) throws URISyntaxException {
        log.debug("REST request to save Activite : {}", activite);
        if (activite.getId() != null) {
            throw new BadRequestAlertException("A new activite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Activite result = activiteService.save(activite);
        return ResponseEntity.created(new URI("/api/activites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /activites : Updates an existing activite.
     *
     * @param activite the activite to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated activite,
     * or with status 400 (Bad Request) if the activite is not valid,
     * or with status 500 (Internal Server Error) if the activite couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/activites")
    @Timed
    public ResponseEntity<Activite> updateActivite(@Valid @RequestBody Activite activite) throws URISyntaxException {
        log.debug("REST request to update Activite : {}", activite);
        if (activite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Activite result = activiteService.save(activite);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, activite.getId().toString()))
            .body(result);
    }

    /**
     * GET  /activites : get all the activites.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of activites in body
     */
    @GetMapping("/activites")
    @Timed
    public ResponseEntity<List<Activite>> getAllActivites(Pageable pageable) {
        log.debug("REST request to get a page of Activites");
        Page<Activite> page = activiteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/activites");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /activites/:id : get the "id" activite.
     *
     * @param id the id of the activite to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the activite, or with status 404 (Not Found)
     */
    @GetMapping("/activites/{id}")
    @Timed
    public ResponseEntity<Activite> getActivite(@PathVariable Long id) {
        log.debug("REST request to get Activite : {}", id);
        Optional<Activite> activite = activiteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(activite);
    }

    /**
     * DELETE  /activites/:id : delete the "id" activite.
     *
     * @param id the id of the activite to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/activites/{id}")
    @Timed
    public ResponseEntity<Void> deleteActivite(@PathVariable Long id) {
        log.debug("REST request to delete Activite : {}", id);
        activiteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
