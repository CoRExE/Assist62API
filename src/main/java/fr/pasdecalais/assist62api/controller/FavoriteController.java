package fr.pasdecalais.assist62api.controller;

import fr.pasdecalais.assist62api.dto.CategoryResponseDTO;
import fr.pasdecalais.assist62api.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des favoris des utilisateurs.
 */
@RestController
@RequestMapping("/api/user/{userId}/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    /**
     * Récupère la liste des favoris pour un utilisateur.
     * @param userId l'identifiant de l'utilisateur
     * @return la liste des catégories favorites
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN') or #userId == authentication.principal.id")
    public ResponseEntity<List<CategoryResponseDTO>> getFavorites(@PathVariable Long userId) {
        return ResponseEntity.ok(favoriteService.getFavorites(userId));
    }

    /**
     * Ajoute une catégorie aux favoris de l'utilisateur.
     * @param userId l'identifiant de l'utilisateur
     * @param categoryId l'identifiant de la catégorie à ajouter
     * @return une réponse sans contenu
     */
    @PostMapping("/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN') or #userId == authentication.principal.id")
    public ResponseEntity<Void> addFavorite(@PathVariable Long userId, @PathVariable Long categoryId) {
        favoriteService.addFavorite(userId, categoryId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retire une catégorie des favoris de l'utilisateur.
     * @param userId l'identifiant de l'utilisateur
     * @param categoryId l'identifiant de la catégorie à retirer
     * @return une réponse sans contenu
     */
    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN') or #userId == authentication.principal.id")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long userId, @PathVariable Long categoryId) {
        favoriteService.removeFavorite(userId, categoryId);
        return ResponseEntity.noContent().build();
    }
}
