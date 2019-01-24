package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Petition;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Petition.
 */
public interface PetitionService {

    /**
     * Save a petition.
     *
     * @param petition the entity to save
     * @return the persisted entity
     */
    Petition save(Petition petition);

    /**
     * Get all the petitions.
     *
     * @return the list of entities
     */
    List<Petition> findAll();


    /**
     * Get the "id" petition.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Petition> findOne(Long id);

    /**
     * Delete the "id" petition.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
