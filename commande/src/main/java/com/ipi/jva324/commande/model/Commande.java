package com.ipi.jva324.commande.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long produitId;

    private long quantite = 0;

    private String status;


    /** cache pour faciliter la modification à la volée par l'utilisateur de la quantité */
    private long quantiteDisponibleStockConnu = 1000;


    /**
     * Aide pour les tests
     * @param produitId
     * @param quantite
     * @param status
     */
    public Commande(Long produitId, long quantite, String status) {
        this.setProduitId(produitId);
        this.setQuantite(quantite);
        this.setStatus(status);
    }

    /**
     * Pour hibernate
     * @param produitId
     * @param quantite
     * @param status
     */
    public Commande() {

    }

    /**
     * Aide pour les tests
     */
    public Commande(Long produitId, long quantite) {
        this.setProduitId(produitId);
        this.setQuantite(quantite);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    public long getQuantite() {
        return quantite;
    }

    public void setQuantite(long quantite) {
        this.quantite = quantite;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public long getQuantiteDisponibleStockConnu() {
        return quantiteDisponibleStockConnu;
    }

    public void setQuantiteDisponibleStockConnu(long quantiteDisponibleStockConnu) {
        this.quantiteDisponibleStockConnu = quantiteDisponibleStockConnu;
    }


    @Override
    public int hashCode() {
        return this.id == null ? 0 : this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Commande) && this.id.equals(((Commande) obj).id);
    }
}
