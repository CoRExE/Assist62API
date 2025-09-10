package fr.pasdecalais.assist62api.dto;

/**
 * DTO pour les réponses de choix de type NAVIGATION.
 */
public class NavigationChoiceResponseDTO extends ChoiceResponseDTO {

    private Long nextStepId;

    // Getters and Setters

    public Long getNextStepId() {
        return nextStepId;
    }

    public void setNextStepId(Long nextStepId) {
        this.nextStepId = nextStepId;
    }
}
