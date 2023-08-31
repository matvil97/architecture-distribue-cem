package com.ipi.jva324.stock.service;

import com.ipi.jva324.stock.model.ProduitEnStock;
import com.ipi.jva324.stock.model.ReceptionDeProduit;

import java.util.List;

public interface ProduitService {
    ProduitEnStock getProduit(long id);

    List<ProduitEnStock> getProduits();

    ProduitEnStock createProduit(ProduitEnStock p);

    ReceptionDeProduit recoitProduit(ReceptionDeProduit rp);
}
