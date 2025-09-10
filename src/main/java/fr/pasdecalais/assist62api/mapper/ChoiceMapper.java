package fr.pasdecalais.assist62api.mapper;

import fr.pasdecalais.assist62api.dto.*;
import fr.pasdecalais.assist62api.model.Choice;
import fr.pasdecalais.assist62api.model.FinalChoice;
import fr.pasdecalais.assist62api.model.NavigationChoice;
import fr.pasdecalais.assist62api.model.UrlChoice;
import org.springframework.stereotype.Component;

/**
 * Mapper pour convertir les entités {@link Choice} en objets {@link ChoiceResponseDTO}.
 * Gère les différents sous-types de choix.
 */
@Component
public class ChoiceMapper {

    /**
     * Convertit une entité {@link Choice} en son {@link ChoiceResponseDTO} correspondant.
     *
     * @param choice l'entité Choice à convertir
     * @return le DTO correspondant, ou {@code null} si l'entité est {@code null}
     * @throws IllegalArgumentException si le type de Choice est inconnu
     */
    public ChoiceResponseDTO toChoiceResponseDTO(Choice choice) {
        switch (choice) {
            case null -> {
                return null;
            }
            case NavigationChoice navigationChoice -> {
                NavigationChoiceResponseDTO dto = new NavigationChoiceResponseDTO();
                dto.setId(navigationChoice.getId());
                dto.setText(navigationChoice.getText());
                if (navigationChoice.getNextStep() != null) {
                    dto.setNextStepId(navigationChoice.getNextStep().getId());
                }
                return dto;
            }
            case UrlChoice urlChoice -> {
                UrlChoiceResponseDTO dto = new UrlChoiceResponseDTO();
                dto.setId(urlChoice.getId());
                dto.setText(urlChoice.getText());
                dto.setUrl(urlChoice.getUrl());
                return dto;
            }
            case FinalChoice finalChoice -> {
                FinalChoiceResponseDTO dto = new FinalChoiceResponseDTO();
                dto.setId(finalChoice.getId());
                dto.setText(finalChoice.getText());
                dto.setConclusionText(finalChoice.getConclusionText());
                return dto;
            }
            default ->
                throw new IllegalArgumentException("Unknown Choice type: " + choice.getClass().getName());
        }
    }
}