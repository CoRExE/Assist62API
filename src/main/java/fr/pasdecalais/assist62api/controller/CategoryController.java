package fr.pasdecalais.assist62api.controller;

import fr.pasdecalais.assist62api.dto.CategoryRequestDTO;
import fr.pasdecalais.assist62api.dto.CategoryResponseDTO;
import fr.pasdecalais.assist62api.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Contrôleur REST pour la gestion des catégories.
 * Fournit des endpoints pour créer, lire, mettre à jour et supprimer des catégories,
 * ainsi que pour récupérer leurs enfants et racines.
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Injection du service de gestion des catégories.
     * @param categoryService le service de catégorie
     */
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Récupère toutes les catégories racines (sans parents).
     * @return liste des catégories racines
     */
    @GetMapping("/root")
    public ResponseEntity<List<CategoryResponseDTO>> getRootCategories() {
        return ResponseEntity.ok(categoryService.getRootCategories());
    }

    /**
     * Récupère toutes les catégories.
     * @return liste de toutes les catégories
     */
    @GetMapping("/all")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    /**
     * Récupère une catégorie par son identifiant.
     * @param id identifiant de la catégorie
     * @return la catégorie correspondante
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    /**
     * Récupère une catégorie par son nom.
     * @param name nom de la catégorie
     * @return la catégorie correspondante
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryResponseDTO> getCategoryByName(@PathVariable String name) {
        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }

    /**
     * Récupère les enfants d'une catégorie donnée.
     * @param id identifiant de la catégorie
     * @return liste des enfants de la catégorie
     */
    @GetMapping("/{id}/children")
    public ResponseEntity<List<CategoryResponseDTO>> getChildren(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getChildrenByCategoryId(id));
    }

    /**
     * Crée une nouvelle catégorie.
     * @param categoryRequestDTO données de la catégorie à créer
     * @return la catégorie créée
     */
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        CategoryResponseDTO createdCategory = categoryService.createCategory(categoryRequestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCategory.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdCategory);
    }

    /**
     * Met à jour une catégorie existante.
     * @param id identifiant de la catégorie à mettre à jour
     * @param categoryRequestDTO nouvelles données de la catégorie
     * @return la catégorie mise à jour
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        CategoryResponseDTO updatedCategory = categoryService.updateCategory(id, categoryRequestDTO);
        return ResponseEntity.ok(updatedCategory);
    }

    /**
     * Supprime une catégorie de façon sécurisée (refuse si la catégorie a des enfants).
     * @param id identifiant de la catégorie à supprimer
     * @return réponse sans contenu
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSafely(@PathVariable Long id) {
        categoryService.deleteSafelyCategory(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Supprime une catégorie et ses enfants (cascade).
     * @param id identifiant de la catégorie à supprimer
     * @return réponse sans contenu
     */
    @DeleteMapping("/{id}/cascade")
    public ResponseEntity<Void> deleteCascade(@PathVariable Long id) {
        categoryService.deleteCascadeCategory(id);
        return ResponseEntity.noContent().build();
    }
}