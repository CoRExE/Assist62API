package fr.pasdecalais.assist62api.controller;

import fr.pasdecalais.assist62api.dto.CategoryResponseDTO;
import fr.pasdecalais.assist62api.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des abonnements des utilisateurs.
 */
@RestController
@RequestMapping("/api/user/{userId}/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    /**
     * Récupère la liste des abonnements pour un utilisateur.
     * @param userId l'identifiant de l'utilisateur
     * @return la liste des catégories auxquelles l'utilisateur est abonné
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN') or #userId == authentication.principal.id")
    public ResponseEntity<List<CategoryResponseDTO>> getSubscriptions(@PathVariable Long userId) {
        return ResponseEntity.ok(subscriptionService.getSubscriptions(userId));
    }

    /**
     * Abonne un utilisateur à une catégorie.
     * @param userId l'identifiant de l'utilisateur
     * @param categoryId l'identifiant de la catégorie
     * @return une réponse sans contenu
     */
    @PostMapping("/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN') or #userId == authentication.principal.id")
    public ResponseEntity<Void> addSubscription(@PathVariable Long userId, @PathVariable Long categoryId) {
        subscriptionService.addSubscription(userId, categoryId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Désabonne un utilisateur d'une catégorie.
     * @param userId l'identifiant de l'utilisateur
     * @param categoryId l'identifiant de la catégorie
     * @return une réponse sans contenu
     */
    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN') or #userId == authentication.principal.id")
    public ResponseEntity<Void> removeSubscription(@PathVariable Long userId, @PathVariable Long categoryId) {
        subscriptionService.removeSubscription(userId, categoryId);
        return ResponseEntity.noContent().build();
    }
}
