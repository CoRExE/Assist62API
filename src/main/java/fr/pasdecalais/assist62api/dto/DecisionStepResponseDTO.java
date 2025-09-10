package fr.pasdecalais.assist62api.dto;

import java.util.List;

/**
 * DTO pour représenter une étape de décision dans les réponses de l'API.
 */
public class DecisionStepResponseDTO {

    private Long id;
    private String text;
    private String imageUrl;
    private boolean isFinal;
    private List<ChoiceResponseDTO> choices;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public List<ChoiceResponseDTO> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceResponseDTO> choices) {
        this.choices = choices;
    }
}
