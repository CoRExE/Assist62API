package fr.pasdecalais.assist62api.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;

/**
 * DTO de base abstrait pour les requêtes de choix.
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = NavigationChoiceRequestDTO.class, name = "NAVIGATION"),
    @JsonSubTypes.Type(value = UrlChoiceRequestDTO.class, name = "URL"),
    @JsonSubTypes.Type(value = FinalChoiceRequestDTO.class, name = "FINAL")
})
public abstract class ChoiceRequestDTO {

    @NotBlank
    private String text;

    @NotNull
    private Long decisionStepId;

    // Getters and Setters

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getDecisionStepId() {
        return decisionStepId;
    }

    public void setDecisionStepId(Long decisionStepId) {
        this.decisionStepId = decisionStepId;
    }
}
