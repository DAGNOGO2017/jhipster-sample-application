package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Activite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Activite.
 */
public interface ActiviteService {

    /**
     * Save a activite.
     *
     * @param activite the entity to save
     * @return the persisted entity
     */
    Activite save(Activite activite);

    /**
     * Get all the activites.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Activite> findAll(Pageable pageable);


    /**
     * Get the "id" activite.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Activite> findOne(Long id);

    /**
     * Delete the "id" activite.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
