package com.ipi.jva324.commande.service;

import com.ipi.jva324.commande.model.Commande;
import com.ipi.jva324.commande.repository.CommandeRepository;
import com.ipi.jva324.stock.model.ProduitEnStock;
import com.ipi.jva324.stock.service.ProduitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommandeServiceImpl {

    protected Logger logger = LoggerFactory.getLogger(CommandeServiceImpl.class);

    /**
     * TODO better
     * filled by event to support random port in tests, rather than by @Value
     */
    private int port;

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private ProduitService produitService;
    @Autowired
    private CommandeProduitService commandeProduitService  ;

    /**
     * @param event
     */
    @EventListener
    void onWebInit(WebServerInitializedEvent event) {
        this.port = event.getWebServer().getPort();
    }


    public List<Commande> getCommandes() {
        return commandeRepository.findAll();
    }

    public Commande getCommande(long id) {
        Optional<Commande> res = commandeRepository.findById(id);
        return res.isPresent() ? res.get() : null;
    }

    public Commande createCommande(Commande commande) {
        commande.setStatus("created");

        logger.debug("createCommande produitId=" + commande.getProduitId());
        ProduitEnStock produitEnStockFound = commandeProduitService.getProduit(commande.getProduitId());
        long quantiteDisponible = (produitEnStockFound == null) ? 0 : produitEnStockFound.getQuantiteDisponible();
        commande.setQuantiteDisponibleStockConnu(quantiteDisponible);

        return commandeRepository.save(commande);
    }

    public Commande validateCommande(Commande commande)
            throws StockInsuffisantCommandeException, CommandeInvalideException {
        if (commande.getQuantite() <= 0) {
            throw new CommandeInvalideException("Doit être à 1 minimum");
        }

        ProduitEnStock produitEnStockFound = produitService.getProduit(commande.getProduitId());
        long quantiteDisponible = (produitEnStockFound == null) ? 0 : produitEnStockFound.getQuantiteDisponible();
        commande.setQuantiteDisponibleStockConnu(quantiteDisponible);
        if (commande.getQuantite() > quantiteDisponible) {
            throw new StockInsuffisantCommandeException();
        }

        commande.setStatus("validated");
        return commandeRepository.save(commande);
    }

    /**
     * @param commande
     * @return
     */
    public Commande updateCommande(Commande commande) {
        return commandeRepository.save(commande);
    }

    /**
     * @param commande
     */
    public void deleteCommande(Commande commande) {
        commandeRepository.deleteById(commande.getId());
    }

}
