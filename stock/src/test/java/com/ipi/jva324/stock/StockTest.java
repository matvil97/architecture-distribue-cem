package com.ipi.jva324.stock;

import com.ipi.jva324.StockApplication;
import com.ipi.jva324.stock.model.ProduitEnStock;
import com.ipi.jva324.stock.repository.ProduitEnStockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class) // Junit 4 : @RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(StockApplication.class)
public class StockTest {

    @Autowired
    private ProduitEnStockRepository produitRepo;

    @BeforeEach
    void setUp() {

    }

    @Test
    public void TestCheckProduit(){
        ProduitEnStock p = new ProduitEnStock("Antis dépresseurs","Pour ne pas sombrer dans la dépression.", 10L);
        produitRepo.save(p);

        List<ProduitEnStock> res = produitRepo.findByNom("Antis dépresseurs");

        assertEquals(res.get(0).getNom(),"Antis dépresseurs");
    }
}
