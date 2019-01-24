package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Petition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Petition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PetitionRepository extends JpaRepository<Petition, Long> {

}
