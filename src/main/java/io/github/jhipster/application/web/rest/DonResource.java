package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Don;
import io.github.jhipster.application.repository.DonRepository;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Don.
 */
@RestController
@RequestMapping("/api")
public class DonResource {

    private final Logger log = LoggerFactory.getLogger(DonResource.class);

    private static final String ENTITY_NAME = "don";

    private final DonRepository donRepository;

    public DonResource(DonRepository donRepository) {
        this.donRepository = donRepository;
    }

    /**
     * POST  /dons : Create a new don.
     *
     * @param don the don to create
     * @return the ResponseEntity with status 201 (Created) and with body the new don, or with status 400 (Bad Request) if the don has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dons")
    @Timed
    public ResponseEntity<Don> createDon(@RequestBody Don don) throws URISyntaxException {
        log.debug("REST request to save Don : {}", don);
        if (don.getId() != null) {
            throw new BadRequestAlertException("A new don cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Don result = donRepository.save(don);
        return ResponseEntity.created(new URI("/api/dons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dons : Updates an existing don.
     *
     * @param don the don to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated don,
     * or with status 400 (Bad Request) if the don is not valid,
     * or with status 500 (Internal Server Error) if the don couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dons")
    @Timed
    public ResponseEntity<Don> updateDon(@RequestBody Don don) throws URISyntaxException {
        log.debug("REST request to update Don : {}", don);
        if (don.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Don result = donRepository.save(don);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, don.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dons : get all the dons.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dons in body
     */
    @GetMapping("/dons")
    @Timed
    public ResponseEntity<List<Don>> getAllDons(Pageable pageable) {
        log.debug("REST request to get a page of Dons");
        Page<Don> page = donRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dons");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /dons/:id : get the "id" don.
     *
     * @param id the id of the don to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the don, or with status 404 (Not Found)
     */
    @GetMapping("/dons/{id}")
    @Timed
    public ResponseEntity<Don> getDon(@PathVariable Long id) {
        log.debug("REST request to get Don : {}", id);
        Optional<Don> don = donRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(don);
    }

    /**
     * DELETE  /dons/:id : delete the "id" don.
     *
     * @param id the id of the don to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dons/{id}")
    @Timed
    public ResponseEntity<Void> deleteDon(@PathVariable Long id) {
        log.debug("REST request to delete Don : {}", id);

        donRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
