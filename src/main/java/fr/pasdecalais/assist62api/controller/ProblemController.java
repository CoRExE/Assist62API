package fr.pasdecalais.assist62api.controller;

import fr.pasdecalais.assist62api.dto.ProblemRequestDTO;
import fr.pasdecalais.assist62api.dto.ProblemResponseDTO;
import fr.pasdecalais.assist62api.service.ProblemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Contrôleur REST pour la gestion des problèmes.
 */
@RestController
@RequestMapping("/api/problem")
public class ProblemController {

    private final ProblemService problemService;

    @Autowired
    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    /**
     * Récupère tous les problèmes.
     * @return liste de tous les problèmes
     */
    @GetMapping("/all")
    public ResponseEntity<List<ProblemResponseDTO>> getAllProblems() {
        return ResponseEntity.ok(problemService.getAllProblems());
    }

    /**
     * Récupère un problème par son identifiant.
     * @param id identifiant du problème
     * @return le problème correspondant
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProblemResponseDTO> getProblemById(@PathVariable Long id) {
        return ResponseEntity.ok(problemService.getProblemById(id));
    }

    /**
     * Récupère tous les problèmes d'une catégorie spécifique.
     * @param categoryId identifiant de la catégorie
     * @return liste des problèmes de la catégorie
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProblemResponseDTO>> getProblemsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(problemService.getProblemsByCategoryId(categoryId));
    }

    /**
     * Récupère tous les problèmes correspondant à un titre spécifique.
     * @param title titre du problème
     * @return liste des problèmes correspondant au titre
     */
    @GetMapping("/title/{title}")
    public ResponseEntity<List<ProblemResponseDTO>> getProblemsByTitle(@PathVariable String title) {
        return ResponseEntity.ok(problemService.getProblemsByTitle(title));
    }

    /**
     * Crée un nouveau problème.
     * @param problemRequestDTO données du problème à créer
     * @return le problème créé
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODO')")
    public ResponseEntity<ProblemResponseDTO> createProblem(@Valid @RequestBody ProblemRequestDTO problemRequestDTO) {
        ProblemResponseDTO createdProblem = problemService.createProblem(problemRequestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdProblem.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdProblem);
    }

    /**
     * Met à jour un problème existant.
     * @param id identifiant du problème à mettre à jour
     * @param problemRequestDTO nouvelles données du problème
     * @return le problème mis à jour
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODO')")
    public ResponseEntity<ProblemResponseDTO> updateProblem(@PathVariable Long id, @Valid @RequestBody ProblemRequestDTO problemRequestDTO) {
        ProblemResponseDTO updatedProblem = problemService.updateProblem(id, problemRequestDTO);
        return ResponseEntity.ok(updatedProblem);
    }

    /**
     * Supprime un problème.
     * @param id identifiant du problème à supprimer
     * @return réponse sans contenu
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODO')")
    public ResponseEntity<Void> deleteProblem(@PathVariable Long id) {
        problemService.deleteProblem(id);
        return ResponseEntity.noContent().build();
    }
}
