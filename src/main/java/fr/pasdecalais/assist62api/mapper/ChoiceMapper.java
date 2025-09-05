package fr.pasdecalais.assist62api.mapper;

import fr.pasdecalais.assist62api.dto.ChoiceResponseDTO;
import fr.pasdecalais.assist62api.model.Choice;
import org.springframework.stereotype.Component;

/**
 * Mapper pour convertir les entités {@link Choice} en objets {@link ChoiceResponseDTO}.
 */
@Component
public class ChoiceMapper {

    /**
     * Convertit une entité {@link Choice} en {@link ChoiceResponseDTO}.
     *
     * @param choice l'entité Choice à convertir
     * @return le DTO correspondant, ou {@code null} si l'entité est {@code null}
     */
    public ChoiceResponseDTO toChoiceResponseDTO(Choice choice) {
        if (choice == null) {
            return null;
        }
        ChoiceResponseDTO dto = new ChoiceResponseDTO();
        dto.setId(choice.getId());
        dto.setText(choice.getText());
        if (choice.getNextStep() != null) {
            dto.setNextStepId(choice.getNextStep().getId());
        }
        return dto;
    }
}
