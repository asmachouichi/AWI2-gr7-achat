package tn.esprit.rh.achat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.rh.achat.entities.Produit;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.ProduitRepository;
import tn.esprit.rh.achat.repositories.StockRepository;
import tn.esprit.rh.achat.services.ProduitServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProduitServiceImplTestMock {

    @InjectMocks
    private ProduitServiceImpl produitService;

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private StockRepository stockRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test addProduit
    @Test
    public void testAddProduit() {
        Produit p = new Produit();
        p.setLibelleProduit("Clavier");
        p.setIdProduit(1L);

        when(produitRepository.save(p)).thenReturn(p);

        Produit saved = produitService.addProduit(p);

        assertNotNull(saved.getIdProduit());
        assertEquals("Clavier", saved.getLibelleProduit());
        verify(produitRepository, times(1)).save(p);
    }

    // Test assignProduitToStock
    @Test
    public void testAssignProduitToStock() {
        Produit produit = new Produit();
        produit.setIdProduit(1L);
        produit.setLibelleProduit("Souris");

        Stock stock = new Stock();
        stock.setIdStock(10L);
        stock.setLibelleStock("Stock A");

        when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));
        when(stockRepository.findById(10L)).thenReturn(Optional.of(stock));
        when(produitRepository.save(produit)).thenReturn(produit);

        produitService.assignProduitToStock(1L, 10L);

        assertEquals(stock, produit.getStock());
        verify(produitRepository, times(1)).save(produit);
    }

    // Test retrieveProduit
    @Test
    public void testRetrieveProduit() {
        Produit produit = new Produit();
        produit.setIdProduit(1L);
        produit.setLibelleProduit("Produit Test");

        when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));

        Produit result = produitService.retrieveProduit(1L);

        assertNotNull(result);
        assertEquals("Produit Test", result.getLibelleProduit());
    }

    // Test retrieveAllProduits
    @Test
    public void testRetrieveAllProduits() {
        Produit p1 = new Produit();
        p1.setIdProduit(1L);
        p1.setLibelleProduit("Produit1");

        Produit p2 = new Produit();
        p2.setIdProduit(2L);
        p2.setLibelleProduit("Produit2");

        when(produitRepository.findAll()).thenReturn(List.of(p1, p2));

        List<Produit> produits = produitService.retrieveAllProduits();

        assertEquals(2, produits.size());
    }

    // Test updateProduit
    @Test
    public void testUpdateProduit() {
        Produit produit = new Produit();
        produit.setIdProduit(1L);
        produit.setLibelleProduit("Ancien");

        Produit updatedProduit = new Produit();
        updatedProduit.setIdProduit(1L);
        updatedProduit.setLibelleProduit("Nouveau");

        when(produitRepository.save(produit)).thenReturn(updatedProduit);

        Produit result = produitService.updateProduit(produit);

        assertEquals("Nouveau", result.getLibelleProduit());
        verify(produitRepository, times(1)).save(produit);
    }

    // Test deleteProduit
    @Test
    public void testDeleteProduit() {
        doNothing().when(produitRepository).deleteById(1L);

        produitService.deleteProduit(1L);

        verify(produitRepository, times(1)).deleteById(1L);
    }
}
