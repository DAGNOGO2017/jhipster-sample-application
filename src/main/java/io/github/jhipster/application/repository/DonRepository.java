package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Don;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Don entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DonRepository extends JpaRepository<Don, Long> {

}
