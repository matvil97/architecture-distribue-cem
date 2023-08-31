package com.ipi.jva324.commande.service;

import com.ipi.jva324.stock.model.ProduitEnStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CommandeProduitServiceRESTHALImpl implements CommandeProduitService {

    /** URL serveur : (configurée dans application.properties, sinon valeur par défaut) */
    @Value("${commande.apiserver.url:http://localhost:8080/}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ProduitEnStock getProduit(long id) {
        ProduitEnStock p = restTemplate.getForObject(url
                + "api/data-rest/produitEnStocks/" + id, ProduitEnStock.class);
        return p;
    }
}
