package com.ipi.jva324.commande.service;

import com.ipi.jva324.stock.model.ProduitEnStock;
import com.ipi.jva324.stock.service.ProduitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandeProduitServiceLocalImpl implements CommandeProduitService{

    protected Logger logger = LoggerFactory.getLogger(CommandeProduitServiceLocalImpl.class);
    @Autowired
    ProduitService produitService;

    @Override
    public ProduitEnStock getProduit(long id) {
        return produitService.getProduit(id);
    }
}
