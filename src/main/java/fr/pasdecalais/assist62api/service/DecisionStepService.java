package fr.pasdecalais.assist62api.service;

import fr.pasdecalais.assist62api.dto.DecisionStepRequestDTO;
import fr.pasdecalais.assist62api.dto.DecisionStepResponseDTO;
import fr.pasdecalais.assist62api.mapper.DecisionStepMapper;
import fr.pasdecalais.assist62api.model.DecisionStep;
import fr.pasdecalais.assist62api.repository.DecisionStepRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service pour la gestion des étapes de décision.
 */
@Service
@Transactional
public class DecisionStepService {

    private static final String STEP_NOT_FOUND_WITH_ID = "DecisionStep not found with id: ";

    private final DecisionStepRepository decisionStepRepository;
    private final DecisionStepMapper decisionStepMapper;

    @Autowired
    public DecisionStepService(DecisionStepRepository decisionStepRepository, DecisionStepMapper decisionStepMapper) {
        this.decisionStepRepository = decisionStepRepository;
        this.decisionStepMapper = decisionStepMapper;
    }

    /**
     * Récupère une étape de décision par son identifiant.
     * @param id l'identifiant de l'étape
     * @return l'étape de décision
     */
    @Transactional(readOnly = true)
    public DecisionStepResponseDTO getDecisionStepById(Long id) {
        DecisionStep decisionStep = decisionStepRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(STEP_NOT_FOUND_WITH_ID + id));
        return decisionStepMapper.toDecisionStepResponseDTO(decisionStep);
    }

    /**
     * Crée une nouvelle étape de décision.
     * @param requestDTO les données de l'étape à créer
     * @return l'étape créée
     */
    public DecisionStepResponseDTO createDecisionStep(DecisionStepRequestDTO requestDTO) {
        DecisionStep decisionStep = new DecisionStep();
        decisionStep.setText(requestDTO.getText());
        decisionStep.setImageUrl(requestDTO.getImageUrl());
        decisionStep.setFinal(requestDTO.isFinal());

        DecisionStep savedDecisionStep = decisionStepRepository.save(decisionStep);
        return decisionStepMapper.toDecisionStepResponseDTO(savedDecisionStep);
    }

    /**
     * Met à jour une étape de décision existante.
     * @param id l'identifiant de l'étape à mettre à jour
     * @param requestDTO les nouvelles données de l'étape
     * @return l'étape mise à jour
     */
    public DecisionStepResponseDTO updateDecisionStep(Long id, DecisionStepRequestDTO requestDTO) {
        DecisionStep existingStep = decisionStepRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(STEP_NOT_FOUND_WITH_ID + id));

        existingStep.setText(requestDTO.getText());
        existingStep.setImageUrl(requestDTO.getImageUrl());
        existingStep.setFinal(requestDTO.isFinal());

        DecisionStep updatedStep = decisionStepRepository.save(existingStep);
        return decisionStepMapper.toDecisionStepResponseDTO(updatedStep);
    }

    /**
     * Supprime une étape de décision.
     * @param id l'identifiant de l'étape à supprimer
     */
    public void deleteDecisionStep(Long id) {
        if (!decisionStepRepository.existsById(id)) {
            throw new EntityNotFoundException(STEP_NOT_FOUND_WITH_ID + id);
        }
        decisionStepRepository.deleteById(id);
    }
}
