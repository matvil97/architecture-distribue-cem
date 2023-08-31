package com.ipi.jva324.commande;

import com.ipi.jva324.Jva324Application;
import com.ipi.jva324.commande.model.Commande;
import com.ipi.jva324.commande.repository.CommandeRepository;
import com.ipi.jva324.commande.service.CommandeInvalideException;
import com.ipi.jva324.commande.service.CommandeProduitService;
import com.ipi.jva324.commande.service.CommandeServiceImpl;
import com.ipi.jva324.commande.service.StockInsuffisantCommandeException;
import com.ipi.jva324.stock.model.ProduitEnStock;
import com.ipi.jva324.stock.repository.ProduitEnStockRepository;
import com.ipi.jva324.stock.service.ProduitService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class) // Junit 4 : @RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(Jva324Application.class)
public class CommandeTest {

    /** TODO rm, pas utile ici */
    @Value(value="${local.server.port}")
    private int port;

    @Autowired
    private CommandeServiceImpl commandeServiceImpl;
    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private ProduitEnStockRepository produitEnStockRepository;

    /** aide pour les tests */
    @Autowired
    private ProduitService produitService;

    @BeforeEach
    void setUp() {

    }

    @Test
    public void testStockSuffisant() throws StockInsuffisantCommandeException, CommandeInvalideException {
        // produit créé en init
        ProduitEnStock p1 = produitService.getProduits().get(0);
        // NB. pas possible de plutôt créer un nouveau produit en test car la tx n'est pas committée
        // (à moins d'appeler en REST ou par un service fermant la tx déclarativement ou explicitement)
        //ProduitEnStock p1 = produitService.createProduit(new ProduitEnStock("Google Pixel 7", null, 2));
        Object res1 = produitEnStockRepository.findAll();//TODO rm

        Commande c1 = commandeServiceImpl.createCommande(new Commande(p1.getId(), 1l));
        assertEquals("created", c1.getStatus());

        commandeServiceImpl.validateCommande(c1);
        assertEquals("validated", c1.getStatus());
    }

    @Test
    public void testStockPasSuffisant() throws CommandeInvalideException {
        // produit créé en init
        ProduitEnStock p1 = produitService.getProduits().get(0);
        // NB. pas possible de plutôt créer un nouveau produit en test car la tx n'est pas committée
        // (à moins d'appeler en REST ou par un service fermant la tx déclarativement ou explicitement)
        //ProduitEnStock p1 = produitService.createProduit(new ProduitEnStock("Google Pixel 7", null, 2));

        Commande c1 = commandeServiceImpl.createCommande(new Commande(p1.getId(), 30l));
        assertEquals("created", c1.getStatus());

        try {
            commandeServiceImpl.validateCommande(c1);
            Assertions.fail("Le stock devrait être insuffisant");
        } catch (StockInsuffisantCommandeException e) {
            assertEquals(true, e.getMessage().contains("insuffisant"));
        }
    }

    @Test
    public void testCreateCommande() {
        // GIVEN
        Commande commande = new Commande(1L, 10, "initial_status");

        // WHEN
        Commande result = commandeServiceImpl.createCommande(commande);

        // THEN
        assertNotNull(result.getId()); // Assuming the id is generated on save
        assertEquals("created", result.getStatus());

        Commande savedCommande = commandeRepository.findById(result.getId()).orElse(null);
        assertNotNull(savedCommande);
        assertEquals("created", savedCommande.getStatus());
    }
}
