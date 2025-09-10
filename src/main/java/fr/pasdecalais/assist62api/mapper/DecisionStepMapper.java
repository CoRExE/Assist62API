package fr.pasdecalais.assist62api.mapper;

import fr.pasdecalais.assist62api.dto.DecisionStepResponseDTO;
import fr.pasdecalais.assist62api.model.DecisionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Mapper pour convertir les entités {@link DecisionStep} en objets {@link DecisionStepResponseDTO}.
 */
@Component
public class DecisionStepMapper {

    private final ChoiceMapper choiceMapper;

    @Autowired
    public DecisionStepMapper(ChoiceMapper choiceMapper) {
        this.choiceMapper = choiceMapper;
    }

    /**
     * Convertit une entité {@link DecisionStep} en {@link DecisionStepResponseDTO}.
     *
     * @param decisionStep l'entité DecisionStep à convertir
     * @return le DTO correspondant, ou {@code null} si l'entité est {@code null}
     */
    public DecisionStepResponseDTO toDecisionStepResponseDTO(DecisionStep decisionStep) {
        if (decisionStep == null) {
            return null;
        }
        DecisionStepResponseDTO dto = new DecisionStepResponseDTO();
        dto.setId(decisionStep.getId());
        dto.setText(decisionStep.getText());
        dto.setImageUrl(decisionStep.getImageUrl());
        dto.setFinal(decisionStep.isFinal());
        if (decisionStep.getChoices() != null) {
            dto.setChoices(decisionStep.getChoices().stream()
                    .map(choiceMapper::toChoiceResponseDTO)
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}
