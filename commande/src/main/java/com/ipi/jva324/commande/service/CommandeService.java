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
public class CommandeService {

    protected Logger logger = LoggerFactory.getLogger(CommandeService.class);

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
    private CommandeProduitService commandeProduitService;

    /**
     * TODO better
     * récupère le port après l'initialisation de Spring (car avant est impossible)
     * https://github.com/spring-projects/spring-boot/issues/29589
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

        // TODO get quantiteStockConnu, d'abord par RestTemplate
        logger.debug("createCommande produitId=" + commande.getProduitId());
        ProduitEnStock produitEnStockFound = commandeProduitService.getProduit(commande.getProduitId());
        long quantiteDisponible = (produitEnStockFound == null) ? 0 : produitEnStockFound.getQuantiteDisponible();
        commande.setQuantiteDisponibleStockConnu(quantiteDisponible);

        return commandeRepository.save(commande);
    }

    public Commande validateCommande(Commande commande)
            throws StockInsuffisantCommandeException, CommandeInvalideException {
        if (commande.getQuantite() <= 0) {
            throw new CommandeInvalideException("quantite doit être au moins 1"); // TODO plutôt Hibernate validator ?
        }

        // vérifie que le stock est suffisant
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
     * Aide, TODO interdire de changer status, quantiteStockConnu...
     * @param commande
     * @return
     */
    public Commande updateCommande(Commande commande) {
        return commandeRepository.save(commande);
    }

    /**
     * Aide, TODO gérer le cas validated ou l'interdire ou supprimer la méthode ?
     * @param commande
     */
    public void deleteCommande(Commande commande) {
        commandeRepository.deleteById(commande.getId());
    }

}
