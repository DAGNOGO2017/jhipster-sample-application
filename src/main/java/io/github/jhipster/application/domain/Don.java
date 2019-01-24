package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Don.
 */
@Entity
@Table(name = "don")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Don implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_don")
    private Integer idDon;

    @Column(name = "nature_don")
    private String natureDon;

    @ManyToOne
    @JsonIgnoreProperties("dons")
    private Personne personne;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdDon() {
        return idDon;
    }

    public Don idDon(Integer idDon) {
        this.idDon = idDon;
        return this;
    }

    public void setIdDon(Integer idDon) {
        this.idDon = idDon;
    }

    public String getNatureDon() {
        return natureDon;
    }

    public Don natureDon(String natureDon) {
        this.natureDon = natureDon;
        return this;
    }

    public void setNatureDon(String natureDon) {
        this.natureDon = natureDon;
    }

    public Personne getPersonne() {
        return personne;
    }

    public Don personne(Personne personne) {
        this.personne = personne;
        return this;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
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
        Don don = (Don) o;
        if (don.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), don.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Don{" +
            "id=" + getId() +
            ", idDon=" + getIdDon() +
            ", natureDon='" + getNatureDon() + "'" +
            "}";
    }
}
