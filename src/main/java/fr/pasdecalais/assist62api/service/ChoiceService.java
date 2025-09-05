package fr.pasdecalais.assist62api.service;

import fr.pasdecalais.assist62api.dto.ChoiceRequestDTO;
import fr.pasdecalais.assist62api.dto.ChoiceResponseDTO;
import fr.pasdecalais.assist62api.mapper.ChoiceMapper;
import fr.pasdecalais.assist62api.model.Choice;
import fr.pasdecalais.assist62api.model.DecisionStep;
import fr.pasdecalais.assist62api.repository.ChoiceRepository;
import fr.pasdecalais.assist62api.repository.DecisionStepRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service pour la gestion des choix.
 */
@Service
@Transactional
public class ChoiceService {

    private static final String CHOICE_NOT_FOUND_WITH_ID = "Choice not found with id: ";
    private static final String STEP_NOT_FOUND_WITH_ID = "DecisionStep not found with id: ";

    private final ChoiceRepository choiceRepository;
    private final DecisionStepRepository decisionStepRepository;
    private final ChoiceMapper choiceMapper;

    @Autowired
    public ChoiceService(ChoiceRepository choiceRepository, DecisionStepRepository decisionStepRepository, ChoiceMapper choiceMapper) {
        this.choiceRepository = choiceRepository;
        this.decisionStepRepository = decisionStepRepository;
        this.choiceMapper = choiceMapper;
    }

    /**
     * Récupère un choix par son identifiant.
     * @param id l'identifiant du choix
     * @return le choix
     */
    @Transactional(readOnly = true)
    public ChoiceResponseDTO getChoiceById(Long id) {
        Choice choice = choiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CHOICE_NOT_FOUND_WITH_ID + id));
        return choiceMapper.toChoiceResponseDTO(choice);
    }

    /**
     * Crée un nouveau choix.
     * @param requestDTO les données du choix à créer
     * @return le choix créé
     */
    public ChoiceResponseDTO createChoice(ChoiceRequestDTO requestDTO) {
        DecisionStep decisionStep = decisionStepRepository.findById(requestDTO.getDecisionStepId())
                .orElseThrow(() -> new EntityNotFoundException(STEP_NOT_FOUND_WITH_ID + requestDTO.getDecisionStepId()));

        DecisionStep nextStep = null;
        if (requestDTO.getNextStepId() != null) {
            nextStep = decisionStepRepository.findById(requestDTO.getNextStepId())
                    .orElseThrow(() -> new EntityNotFoundException(STEP_NOT_FOUND_WITH_ID + requestDTO.getNextStepId()));
        }

        Choice choice = new Choice();
        choice.setText(requestDTO.getText());
        choice.setDecisionStep(decisionStep);
        choice.setNextStep(nextStep);

        Choice savedChoice = choiceRepository.save(choice);
        return choiceMapper.toChoiceResponseDTO(savedChoice);
    }

    /**
     * Met à jour un choix existant.
     * @param id l'identifiant du choix à mettre à jour
     * @param requestDTO les nouvelles données du choix
     * @return le choix mis à jour
     */
    public ChoiceResponseDTO updateChoice(Long id, ChoiceRequestDTO requestDTO) {
        Choice existingChoice = choiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CHOICE_NOT_FOUND_WITH_ID + id));

        DecisionStep decisionStep = decisionStepRepository.findById(requestDTO.getDecisionStepId())
                .orElseThrow(() -> new EntityNotFoundException(STEP_NOT_FOUND_WITH_ID + requestDTO.getDecisionStepId()));

        DecisionStep nextStep = null;
        if (requestDTO.getNextStepId() != null) {
            nextStep = decisionStepRepository.findById(requestDTO.getNextStepId())
                    .orElseThrow(() -> new EntityNotFoundException(STEP_NOT_FOUND_WITH_ID + requestDTO.getNextStepId()));
        }

        existingChoice.setText(requestDTO.getText());
        existingChoice.setDecisionStep(decisionStep);
        existingChoice.setNextStep(nextStep);

        Choice updatedChoice = choiceRepository.save(existingChoice);
        return choiceMapper.toChoiceResponseDTO(updatedChoice);
    }

    /**
     * Supprime un choix.
     * @param id l'identifiant du choix à supprimer
     */
    public void deleteChoice(Long id) {
        if (!choiceRepository.existsById(id)) {
            throw new EntityNotFoundException(CHOICE_NOT_FOUND_WITH_ID + id);
        }
        choiceRepository.deleteById(id);
    }
}
