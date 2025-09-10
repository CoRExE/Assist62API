package fr.pasdecalais.assist62api.dto;

/**
 * DTO pour les requêtes de création/mise à jour de choix de type NAVIGATION.
 */
public class NavigationChoiceRequestDTO extends ChoiceRequestDTO {

    private Long nextStepId;

    // Getters and Setters

    public Long getNextStepId() {
        return nextStepId;
    }

    public void setNextStepId(Long nextStepId) {
        this.nextStepId = nextStepId;
    }
}
