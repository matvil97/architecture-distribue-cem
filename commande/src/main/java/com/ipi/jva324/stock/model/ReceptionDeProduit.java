package com.ipi.jva324.stock.model;

import com.ipi.jva324.stock.repository.ReceptionDeProduitRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class ReceptionDeProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long idProduit;

    private String idProduitExterne;

    private long quantiteRecue = 0;

    private LocalDateTime timestamp;


    /**
     * Aide pour les tests
     * @param idProduit
     * @param idProduitExterne
     * @param quantiteRecue
     * @param timestamp
     */
    public ReceptionDeProduit(long idProduit, String idProduitExterne,
                              long quantiteRecue, LocalDateTime timestamp) {
        this.setIdProduit(idProduit);
        this.setIdProduitExterne(idProduitExterne);
        this.setQuantiteRecue(quantiteRecue);
        this.setTimestamp(timestamp);
    }

    /**
     * Pour hibernate
     */
    public ReceptionDeProduit() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }

    public String getIdProduitExterne() {
        return idProduitExterne;
    }

    public void setIdProduitExterne(String idProduitExterne) {
        this.idProduitExterne = idProduitExterne;
    }

    public long getQuantiteRecue() {
        return quantiteRecue;
    }

    public void setQuantiteRecue(long quantiteRecue) {
        this.quantiteRecue = quantiteRecue;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int hashCode() {
        return this.id == null ? 0 : this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ReceptionDeProduit) && this.id.equals(((ReceptionDeProduit) obj).id);
    }
}