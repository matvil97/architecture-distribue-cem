package com.ipi.jva324.stock.service;

import com.ipi.jva324.stock.model.ProduitEnStock;
import com.ipi.jva324.stock.model.ReceptionDeProduit;
import com.ipi.jva324.stock.repository.ProduitEnStockRepository;
import com.ipi.jva324.stock.repository.ReceptionDeProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Ajoute des données de test si vide au démarrage
 */
@Component
public class StockInitService implements CommandLineRunner {

    @Autowired
    private ProduitService produitService;

    @Override
    public void run(String... strings) throws Exception {
        if (!this.produitService.getProduits().isEmpty()) {
            return;
        }

        ProduitEnStock p1 = this.produitService.createProduit(new ProduitEnStock("iPhone 13 Pro", null, 0));
        ProduitEnStock p2 = this.produitService.createProduit(new ProduitEnStock("Google Pixel 6", null, 0));
        ProduitEnStock p3 = this.produitService.createProduit(new ProduitEnStock("Xiaomi Redmi Note 11", null, 0));

        this.produitService.recoitProduit(new ReceptionDeProduit(p1.getId(), "apple_ip13p", 10, null));
        this.produitService.recoitProduit(new ReceptionDeProduit(p2.getId(), "google_pixel_6", 3, null));
        this.produitService.recoitProduit(new ReceptionDeProduit(p3.getId(), "xiaomi_EHRTUAB", 5, null));
    }
}
