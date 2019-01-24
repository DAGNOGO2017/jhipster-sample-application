package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Personne;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Personne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonneRepository extends JpaRepository<Personne, Long> {

    @Query(value = "select distinct personne from Personne personne left join fetch personne.activites left join fetch personne.petitions",
        countQuery = "select count(distinct personne) from Personne personne")
    Page<Personne> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct personne from Personne personne left join fetch personne.activites left join fetch personne.petitions")
    List<Personne> findAllWithEagerRelationships();

    @Query("select personne from Personne personne left join fetch personne.activites left join fetch personne.petitions where personne.id =:id")
    Optional<Personne> findOneWithEagerRelationships(@Param("id") Long id);

}
