package tn.esprit.rh.achat.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.rh.achat.entities.Produit;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.CategorieProduitRepository;
import tn.esprit.rh.achat.repositories.ProduitRepository;
import tn.esprit.rh.achat.repositories.StockRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class ProduitServiceImpl implements IProduitService {

    @Autowired
    ProduitRepository produitRepository;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    CategorieProduitRepository categorieProduitRepository;

    @Override
    public List<Produit> retrieveAllProduits() {
        log.info("🟦 [DEBUT] Récupération de tous les produits");
        List<Produit> produits = (List<Produit>) produitRepository.findAll();
        for (Produit produit : produits) {
            log.info("➡️ Produit récupéré : {}", produit.getLibelleProduit());
        }
        log.info("🟩 [FIN] Récupération terminée : {} produits trouvés", produits.size());
        return produits;
    }

    @Transactional
    @Override
    public Produit addProduit(Produit p) {
        log.info("🟧 [DEBUT] Ajout d’un nouveau produit : {}", p.getLibelleProduit());
        produitRepository.save(p);
        log.info("🟩 [SUCCÈS] Produit ajouté avec ID : {}", p.getIdProduit());
        return p;
    }

    @Override
    public void deleteProduit(Long produitId) {
        log.info("🟥 [SUPPRESSION] Tentative de suppression du produit avec ID : {}", produitId);
        produitRepository.deleteById(produitId);
        log.info("🟩 [SUCCÈS] Produit supprimé avec ID : {}", produitId);
    }

    @Override
    public Produit updateProduit(Produit p) {
        log.info("🟨 [MISE À JOUR] Mise à jour du produit ID : {}", p.getIdProduit());
        Produit updatedProduit = produitRepository.save(p);
        log.info("🟩 [SUCCÈS] Produit mis à jour : {}", updatedProduit.getLibelleProduit());
        return updatedProduit;
    }

    @Override
    public Produit retrieveProduit(Long produitId) {
        log.info("🟦 [RECHERCHE] Récupération du produit avec ID : {}", produitId);
        Produit produit = produitRepository.findById(produitId).orElse(null);
        if (produit != null)
            log.info("🟩 [SUCCÈS] Produit trouvé : {}", produit.getLibelleProduit());
        else
            log.warn("⚠️ [ERREUR] Aucun produit trouvé pour l’ID : {}", produitId);
        return produit;
    }

    @Override
    public void assignProduitToStock(Long idProduit, Long idStock) {
        log.info("🟦 [AFFECTATION] Produit ID {} → Stock ID {}", idProduit, idStock);
        Produit produit = produitRepository.findById(idProduit).orElse(null);
        Stock stock = stockRepository.findById(idStock).orElse(null);

        if (produit == null || stock == null) {
            log.warn("⚠️ Impossible d’affecter le produit au stock (produit ou stock introuvable)");
            return;
        }

        produit.setStock(stock);
        produitRepository.save(produit);
        log.info("🟩 [SUCCÈS] Produit {} affecté au stock {}", produit.getLibelleProduit(), stock.getLibelleStock());
    }
}
