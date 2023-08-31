package com.ipi.jva324.commande.web;

import com.ipi.jva324.commande.model.Commande;
import com.ipi.jva324.commande.service.CommandeInvalideException;
import com.ipi.jva324.commande.service.CommandeProduitService;
import com.ipi.jva324.commande.service.CommandeServiceImpl;
import com.ipi.jva324.commande.service.StockInsuffisantCommandeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/commandes")
public class CommandeApi {

    @Autowired
    private CommandeServiceImpl commandeServiceImpl;

    @PostMapping
    public Commande createCommande(@RequestBody Commande commande) {
        return commandeServiceImpl.createCommande(commande);
    }

    @PutMapping("{id}")
    public Commande updateCommande(@RequestBody Commande commande) { // without @RequestBody, commande is not filled by auto databinding (though it would be in a web @Controller)
        return commandeServiceImpl.updateCommande(commande);
    }

    @PostMapping("{id}/validate")
    public Commande validateCommande(@RequestBody Commande commande) // without @RequestBody, commande is not filled by auto databinding (though it would be in a web @Controller)
            throws StockInsuffisantCommandeException, CommandeInvalideException {
        return commandeServiceImpl.validateCommande(commande);
    }

    /** que pour tester à ce stade */
    @GetMapping
    public List<Commande> getCommandes() {
        return commandeServiceImpl.getCommandes();
    }

}
