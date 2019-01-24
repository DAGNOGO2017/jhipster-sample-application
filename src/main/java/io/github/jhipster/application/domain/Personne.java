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
 * A Personne.
 */
@Entity
@Table(name = "personne")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Personne implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_personne")
    private Integer idPersonne;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @OneToMany(mappedBy = "personne")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Don> dons = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "personne_activite",
               joinColumns = @JoinColumn(name = "personnes_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "activites_id", referencedColumnName = "id"))
    private Set<Activite> activites = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "personne_petition",
               joinColumns = @JoinColumn(name = "personnes_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "petitions_id", referencedColumnName = "id"))
    private Set<Petition> petitions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdPersonne() {
        return idPersonne;
    }

    public Personne idPersonne(Integer idPersonne) {
        this.idPersonne = idPersonne;
        return this;
    }

    public void setIdPersonne(Integer idPersonne) {
        this.idPersonne = idPersonne;
    }

    public String getNom() {
        return nom;
    }

    public Personne nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Personne prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Set<Don> getDons() {
        return dons;
    }

    public Personne dons(Set<Don> dons) {
        this.dons = dons;
        return this;
    }

    public Personne addDon(Don don) {
        this.dons.add(don);
        don.setPersonne(this);
        return this;
    }

    public Personne removeDon(Don don) {
        this.dons.remove(don);
        don.setPersonne(null);
        return this;
    }

    public void setDons(Set<Don> dons) {
        this.dons = dons;
    }

    public Set<Activite> getActivites() {
        return activites;
    }

    public Personne activites(Set<Activite> activites) {
        this.activites = activites;
        return this;
    }

    public Personne addActivite(Activite activite) {
        this.activites.add(activite);
        activite.getPersonnes().add(this);
        return this;
    }

    public Personne removeActivite(Activite activite) {
        this.activites.remove(activite);
        activite.getPersonnes().remove(this);
        return this;
    }

    public void setActivites(Set<Activite> activites) {
        this.activites = activites;
    }

    public Set<Petition> getPetitions() {
        return petitions;
    }

    public Personne petitions(Set<Petition> petitions) {
        this.petitions = petitions;
        return this;
    }

    public Personne addPetition(Petition petition) {
        this.petitions.add(petition);
        petition.getPersonnes().add(this);
        return this;
    }

    public Personne removePetition(Petition petition) {
        this.petitions.remove(petition);
        petition.getPersonnes().remove(this);
        return this;
    }

    public void setPetitions(Set<Petition> petitions) {
        this.petitions = petitions;
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
        Personne personne = (Personne) o;
        if (personne.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personne.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Personne{" +
            "id=" + getId() +
            ", idPersonne=" + getIdPersonne() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            "}";
    }
}
