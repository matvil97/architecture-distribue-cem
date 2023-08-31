package com.ipi.jva324.stock.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

/**
 * TODO codeProduit, tags...
 */
@Entity
public class ProduitEnStock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nom;

    private String description;

    private long quantiteDisponible = 0;


    /**
     * Aide pour les tests
     * @param nom
     * @param description
     * @param quantiteDisponible
     */
    public ProduitEnStock(String nom, String description, long quantiteDisponible) {
        this.setNom(nom);
        this.setDescription(description);
        this.setQuantiteDisponible(quantiteDisponible);
    }

    /**
     * Pour hibernate
     */
    public ProduitEnStock() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getQuantiteDisponible() {
        return quantiteDisponible;
    }

    public void setQuantiteDisponible(long quantiteDisponible) {
        this.quantiteDisponible = quantiteDisponible;
    }

    @Override
    public int hashCode() {
        return this.id == null ? 0 : this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ProduitEnStock) && this.id.equals(((ProduitEnStock) obj).id);
    }
}