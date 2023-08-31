package com.ipi.jva324.commande;

import com.ipi.jva324.commande.service.CommandeProduitService;
import com.ipi.jva324.stock.model.ProduitEnStock;
import com.ipi.jva324.stock.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class CommandeProduitServiceLocalImpl implements CommandeProduitService {
    @Autowired
    private ProduitService produitService;
    @Override
    public ProduitEnStock getProduit(long id) {
        return produitService.getProduit(id);
    }
}
