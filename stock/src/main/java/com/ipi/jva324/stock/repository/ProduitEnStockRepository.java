package com.ipi.jva324.stock.repository;

import com.ipi.jva324.stock.model.ProduitEnStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProduitEnStockRepository extends JpaRepository<ProduitEnStock,Long> {

    public List<ProduitEnStock> findByNom(String nom);

}
