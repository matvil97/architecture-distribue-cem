package com.ipi.jva324.commande.service;

import com.ipi.jva324.commande.model.Commande;
import com.ipi.jva324.commande.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Ajoute des données de test si vide au démarrage
 */
@Component
public class CommandeInitService implements CommandLineRunner {

    @Autowired
    private CommandeService commandeService;
    @Autowired
    private CommandeRepository commandeRepository;

    @Override
    public void run(String... strings) throws Exception {
        if (!this.commandeService.getCommandes().isEmpty()) {
            return;
        }

        // data init :
        // en utilisant le repository, car le service utilise la couche REST qui n'est pas encore démarrée
        Commande c1 = new Commande(1l, 0, "test");
        c1.setStatus("created");
        commandeRepository.save(c1);
        //Commande c1 = this.commandeService.createCommande(c1); // NO REST client not available yet
    }
}
