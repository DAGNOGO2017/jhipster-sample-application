package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.PetitionService;
import io.github.jhipster.application.domain.Petition;
import io.github.jhipster.application.repository.PetitionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Petition.
 */
@Service
@Transactional
public class PetitionServiceImpl implements PetitionService {

    private final Logger log = LoggerFactory.getLogger(PetitionServiceImpl.class);

    private final PetitionRepository petitionRepository;

    public PetitionServiceImpl(PetitionRepository petitionRepository) {
        this.petitionRepository = petitionRepository;
    }

    /**
     * Save a petition.
     *
     * @param petition the entity to save
     * @return the persisted entity
     */
    @Override
    public Petition save(Petition petition) {
        log.debug("Request to save Petition : {}", petition);
        return petitionRepository.save(petition);
    }

    /**
     * Get all the petitions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Petition> findAll() {
        log.debug("Request to get all Petitions");
        return petitionRepository.findAll();
    }


    /**
     * Get one petition by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Petition> findOne(Long id) {
        log.debug("Request to get Petition : {}", id);
        return petitionRepository.findById(id);
    }

    /**
     * Delete the petition by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Petition : {}", id);
        petitionRepository.deleteById(id);
    }
}
