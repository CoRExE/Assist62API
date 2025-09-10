package fr.pasdecalais.assist62api.controller;

import fr.pasdecalais.assist62api.dto.DecisionStepRequestDTO;
import fr.pasdecalais.assist62api.dto.DecisionStepResponseDTO;
import fr.pasdecalais.assist62api.service.DecisionStepService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Contrôleur REST pour la gestion des étapes de décision.
 */
@RestController
@RequestMapping("/api/step")
public class DecisionStepController {

    private final DecisionStepService decisionStepService;

    @Autowired
    public DecisionStepController(DecisionStepService decisionStepService) {
        this.decisionStepService = decisionStepService;
    }

    /**
     * Récupère une étape de décision par son identifiant.
     * @param id identifiant de l'étape
     * @return l'étape de décision correspondante
     */
    @GetMapping("/{id}")
    public ResponseEntity<DecisionStepResponseDTO> getDecisionStepById(@PathVariable Long id) {
        return ResponseEntity.ok(decisionStepService.getDecisionStepById(id));
    }

    /**
     * Crée une nouvelle étape de décision.
     * @param requestDTO données de l'étape à créer
     * @return l'étape créée
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODO')")
    public ResponseEntity<DecisionStepResponseDTO> createDecisionStep(@Valid @RequestBody DecisionStepRequestDTO requestDTO) {
        DecisionStepResponseDTO createdStep = decisionStepService.createDecisionStep(requestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdStep.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdStep);
    }

    /**
     * Met à jour une étape de décision existante.
     * @param id identifiant de l'étape à mettre à jour
     * @param requestDTO nouvelles données de l'étape
     * @return l'étape mise à jour
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODO')")
    public ResponseEntity<DecisionStepResponseDTO> updateDecisionStep(@PathVariable Long id, @Valid @RequestBody DecisionStepRequestDTO requestDTO) {
        DecisionStepResponseDTO updatedStep = decisionStepService.updateDecisionStep(id, requestDTO);
        return ResponseEntity.ok(updatedStep);
    }

    /**
     * Supprime une étape de décision.
     * @param id identifiant de l'étape à supprimer
     * @return réponse sans contenu
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODO')")
    public ResponseEntity<Void> deleteDecisionStep(@PathVariable Long id) {
        decisionStepService.deleteDecisionStep(id);
        return ResponseEntity.noContent().build();
    }
}
