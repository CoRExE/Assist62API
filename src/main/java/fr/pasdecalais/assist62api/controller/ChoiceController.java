package fr.pasdecalais.assist62api.controller;

import fr.pasdecalais.assist62api.dto.ChoiceRequestDTO;
import fr.pasdecalais.assist62api.dto.ChoiceResponseDTO;
import fr.pasdecalais.assist62api.service.ChoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Contrôleur REST pour la gestion des choix.
 */
@RestController
@RequestMapping("/api/choice")
public class ChoiceController {

    private final ChoiceService choiceService;

    @Autowired
    public ChoiceController(ChoiceService choiceService) {
        this.choiceService = choiceService;
    }

    /**
     * Récupère un choix par son identifiant.
     * @param id identifiant du choix
     * @return le choix correspondant
     */
    @GetMapping("/{id}")
    public ResponseEntity<ChoiceResponseDTO> getChoiceById(@PathVariable Long id) {
        return ResponseEntity.ok(choiceService.getChoiceById(id));
    }

    /**
     * Récupère tous les choix d'un type donné.
     * @param type type des choix à récupérer
     * @return liste des choix correspondants
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<List<ChoiceResponseDTO>> getChoiceByType(@PathVariable String type) {
        return ResponseEntity.ok(choiceService.getChoicesByType(type));
    }

    /**
     * Crée un nouveau choix.
     * @param requestDTO données du choix à créer
     * @return le choix créé
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODO')")
    public ResponseEntity<ChoiceResponseDTO> createChoice(@Valid @RequestBody ChoiceRequestDTO requestDTO) {
        ChoiceResponseDTO createdChoice = choiceService.createChoice(requestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdChoice.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdChoice);
    }

    /**
     * Met à jour un choix existant.
     * @param id identifiant du choix à mettre à jour
     * @param requestDTO nouvelles données du choix
     * @return le choix mis à jour
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODO')")
    public ResponseEntity<ChoiceResponseDTO> updateChoice(@PathVariable Long id, @Valid @RequestBody ChoiceRequestDTO requestDTO) {
        ChoiceResponseDTO updatedChoice = choiceService.updateChoice(id, requestDTO);
        return ResponseEntity.ok(updatedChoice);
    }

    /**
     * Supprime un choix.
     * @param id identifiant du choix à supprimer
     * @return réponse sans contenu
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODO')")
    public ResponseEntity<Void> deleteChoice(@PathVariable Long id) {
        choiceService.deleteChoice(id);
        return ResponseEntity.noContent().build();
    }
}
