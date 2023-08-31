package com.ipi.jva324.stock.service;

import com.ipi.jva324.stock.model.ProduitEnStock;
import com.ipi.jva324.stock.model.ReceptionDeProduit;
import com.ipi.jva324.stock.repository.ProduitEnStockRepository;
import com.ipi.jva324.stock.repository.ReceptionDeProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * TODO rebuild quantite...
 */
@Service
public class ProduitService {

    @Autowired
    private ProduitEnStockRepository produitEnStockRepository;

    @Autowired
    private ReceptionDeProduitRepository receptionDeProduitRepository;

    public List<ProduitEnStock> getProduits() {
        return produitEnStockRepository.findAll();
    }

    public ProduitEnStock getProduit(long id) {
        Optional<ProduitEnStock> res = produitEnStockRepository.findById(id);
        return res.isPresent() ? res.get() : null;
    }

    public ProduitEnStock createProduit(ProduitEnStock p) {
        return produitEnStockRepository.save(p);
    }

    public ProduitEnStock updateProduit(ProduitEnStock p) {
        return produitEnStockRepository.save(p);
    }

    public void deleteProduit(ProduitEnStock p) {
        produitEnStockRepository.deleteById(p.getId());
    }

    /**
     * transactionnel
     * TODO le type devrait être mappé... en pratique à la première commande fournisseur,
     * sinon auto au réapprovisionnement auprès du même fournisseur
     * @param rp
     */
    public ReceptionDeProduit recoitProduit(ReceptionDeProduit rp) {
        Optional<ProduitEnStock> found = produitEnStockRepository.findById(rp.getIdProduit());
        if (!found.isPresent()) {
            throw new RuntimeException("Creez le produit d'abord !");
        }

        if (rp.getTimestamp() == null) {
            rp.setTimestamp(LocalDateTime.now());
        }

        ProduitEnStock p = found.get();
        p.setQuantiteDisponible(p.getQuantiteDisponible() + rp.getQuantiteRecue());
        produitEnStockRepository.save(p);

        return receptionDeProduitRepository.save(rp);
    }
}
