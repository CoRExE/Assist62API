package fr.pasdecalais.assist62api.dto;

/**
 * DTO pour représenter un choix dans les réponses de l'API.
 */
public class ChoiceResponseDTO {

    private Long id;
    private String text;
    private Long nextStepId;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
