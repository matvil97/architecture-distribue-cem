package com.ipi.jva324.stock.repository;

import com.ipi.jva324.stock.model.ProduitEnStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Aussi auto expose en REST par Spring Data REST, appelé par CommandeService
 * ainsi :
 *
 * /produitEnStocks
 * /produitEnStocks/1
 * /produitEnStocks/search/findByNom?nom=x
 *
 * https://docs.spring.io/spring-data/rest/docs/current/reference/html/#repository-resources.search-resource
 * https://stackoverflow.com/questions/55826460/spring-data-rest-configure-findby-path
 */
public interface ProduitEnStockRepository extends JpaRepository<ProduitEnStock,Long> {

    public List<ProduitEnStock> findByNom(String nom);

}
