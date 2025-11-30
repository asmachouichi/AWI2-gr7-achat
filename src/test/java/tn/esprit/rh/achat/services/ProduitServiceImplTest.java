package tn.esprit.rh.achat.services;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.rh.achat.entities.Produit;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test") // ✅ utilise la base H2 pour les tests
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProduitServiceImplTest {

    @Autowired
    private IProduitService produitService;

    @Autowired
    private StockRepository stockRepository;

    private static Long produitId;
    private static Long stockId;

    @Test
    @Order(1)
    public void testAddProduit() {
        Produit produit = new Produit();
        produit.setLibelleProduit("Test Produit");
        produit.setPrix(1500.0f);

        Produit savedProduit = produitService.addProduit(produit);

        Assertions.assertNotNull(savedProduit.getIdProduit(), "L'ID du produit ne doit pas être nul");
        Assertions.assertEquals("Test Produit", savedProduit.getLibelleProduit());

        produitId = savedProduit.getIdProduit();
    }

    @Test
    @Order(2)
    public void testRetrieveProduit() {
        Produit produit = produitService.retrieveProduit(produitId);
        Assertions.assertNotNull(produit, "Le produit doit être récupéré");
        Assertions.assertEquals("Test Produit", produit.getLibelleProduit());
    }

    @Test
    @Order(3)
    public void testRetrieveAllProduits() {
        List<Produit> produits = produitService.retrieveAllProduits();
        Assertions.assertFalse(produits.isEmpty(), "La liste des produits ne doit pas être vide");
    }

    @Test
    @Order(4)
    public void testUpdateProduit() {
        Produit produit = produitService.retrieveProduit(produitId);
        produit.setLibelleProduit("Produit Mis à Jour");

        Produit updatedProduit = produitService.updateProduit(produit);
        Assertions.assertEquals("Produit Mis à Jour", updatedProduit.getLibelleProduit());
    }

    @Test
    @Order(5)
    public void testAssignProduitToStock() {
        Stock stock = new Stock();
        stock.setLibelleStock("Stock Test");
        stock = stockRepository.save(stock);
        stockId = stock.getIdStock();

        produitService.assignProduitToStock(produitId, stockId);

        Produit produit = produitService.retrieveProduit(produitId);
        Assertions.assertNotNull(produit.getStock(), "Le produit doit être affecté à un stock");
        Assertions.assertEquals(stockId, produit.getStock().getIdStock());
    }

    @Test
    @Order(6)
    public void testDeleteProduit() {
        produitService.deleteProduit(produitId);
        Produit deletedProduit = produitService.retrieveProduit(produitId);
        Assertions.assertNull(deletedProduit, "Le produit doit être supprimé");
    }
}
