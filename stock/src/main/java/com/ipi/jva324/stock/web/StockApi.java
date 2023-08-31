package com.ipi.jva324.stock.web;

import com.ipi.jva324.stock.model.ProduitEnStock;
import com.ipi.jva324.stock.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/stock")
public class StockApi {

    @Autowired
    private ProduitService produitService;

    @GetMapping(value = "/")
    public List<ProduitEnStock> getProducts(){
        return  produitService.getProduits();
    }
}
