package com.ipi.jva324.commande.repository;

import com.ipi.jva324.commande.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Aussi auto expose en REST par Spring Data REST, appelé par l'IHM React.js
 * /commandes
 * /commandes/1
 */
public interface CommandeRepository extends JpaRepository<Commande,Long> {



}
