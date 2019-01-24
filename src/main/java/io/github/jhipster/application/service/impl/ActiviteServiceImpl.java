package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ActiviteService;
import io.github.jhipster.application.domain.Activite;
import io.github.jhipster.application.repository.ActiviteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Activite.
 */
@Service
@Transactional
public class ActiviteServiceImpl implements ActiviteService {

    private final Logger log = LoggerFactory.getLogger(ActiviteServiceImpl.class);

    private final ActiviteRepository activiteRepository;

    public ActiviteServiceImpl(ActiviteRepository activiteRepository) {
        this.activiteRepository = activiteRepository;
    }

    /**
     * Save a activite.
     *
     * @param activite the entity to save
     * @return the persisted entity
     */
    @Override
    public Activite save(Activite activite) {
        log.debug("Request to save Activite : {}", activite);
        return activiteRepository.save(activite);
    }

    /**
     * Get all the activites.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Activite> findAll(Pageable pageable) {
        log.debug("Request to get all Activites");
        return activiteRepository.findAll(pageable);
    }


    /**
     * Get one activite by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Activite> findOne(Long id) {
        log.debug("Request to get Activite : {}", id);
        return activiteRepository.findById(id);
    }

    /**
     * Delete the activite by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Activite : {}", id);
        activiteRepository.deleteById(id);
    }
}
