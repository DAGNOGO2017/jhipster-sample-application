package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "activite")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Activite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_activite")
    private Integer idActivite;

    @NotNull
    @Column(name = "nom_activite", nullable = false)
    private String nomActivite;

    @ManyToMany(mappedBy = "activites")
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

    public Integer getIdActivite() {
        return idActivite;
    }

    public Activite idActivite(Integer idActivite) {
        this.idActivite = idActivite;
        return this;
    }

    public void setIdActivite(Integer idActivite) {
        this.idActivite = idActivite;
    }

    public String getNomActivite() {
        return nomActivite;
    }

    public Activite nomActivite(String nomActivite) {
        this.nomActivite = nomActivite;
        return this;
    }

    public void setNomActivite(String nomActivite) {
        this.nomActivite = nomActivite;
    }

    public Set<Personne> getPersonnes() {
        return personnes;
    }

    public Activite personnes(Set<Personne> personnes) {
        this.personnes = personnes;
        return this;
    }

    public Activite addPersonne(Personne personne) {
        this.personnes.add(personne);
        personne.getActivites().add(this);
        return this;
    }

    public Activite removePersonne(Personne personne) {
        this.personnes.remove(personne);
        personne.getActivites().remove(this);
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
        Activite activite = (Activite) o;
        if (activite.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activite.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Activite{" +
            "id=" + getId() +
            ", idActivite=" + getIdActivite() +
            ", nomActivite='" + getNomActivite() + "'" +
            "}";
    }
}
