package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Petition.
 */
@Entity
@Table(name = "petition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Petition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_petition")
    private Integer idPetition;

    @Column(name = "libelepetition")
    private String libelepetition;

    @ManyToMany(mappedBy = "petitions")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Personne> personnes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdPetition() {
        return idPetition;
    }

    public Petition idPetition(Integer idPetition) {
        this.idPetition = idPetition;
        return this;
    }

    public void setIdPetition(Integer idPetition) {
        this.idPetition = idPetition;
    }

    public String getLibelepetition() {
        return libelepetition;
    }

    public Petition libelepetition(String libelepetition) {
        this.libelepetition = libelepetition;
        return this;
    }

    public void setLibelepetition(String libelepetition) {
        this.libelepetition = libelepetition;
    }

    public Set<Personne> getPersonnes() {
        return personnes;
    }

    public Petition personnes(Set<Personne> personnes) {
        this.personnes = personnes;
        return this;
    }

    public Petition addPersonne(Personne personne) {
        this.personnes.add(personne);
        personne.getPetitions().add(this);
        return this;
    }

    public Petition removePersonne(Personne personne) {
        this.personnes.remove(personne);
        personne.getPetitions().remove(this);
        return this;
    }

    public void setPersonnes(Set<Personne> personnes) {
        this.personnes = personnes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Petition petition = (Petition) o;
        if (petition.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), petition.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Petition{" +
            "id=" + getId() +
            ", idPetition=" + getIdPetition() +
            ", libelepetition='" + getLibelepetition() + "'" +
            "}";
    }
}
