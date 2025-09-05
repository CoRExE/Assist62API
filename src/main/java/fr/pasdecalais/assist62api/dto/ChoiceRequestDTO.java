package fr.pasdecalais.assist62api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO pour la création ou la mise à jour d'un choix.
 */
public class ChoiceRequestDTO {

    @NotBlank
    private String text;

    private Long nextStepId;

    @NotNull
    private Long decisionStepId;

    // Getters and Setters

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getNextStepId() {
        return nextStepId;
    }

    public void setNextStepId(Long nextStepId) {
        this.nextStepId = nextStepId;
    }

    public Long getDecisionStepId() {
        return decisionStepId;
    }

    public void setDecisionStepId(Long decisionStepId) {
        this.decisionStepId = decisionStepId;
    }
}
