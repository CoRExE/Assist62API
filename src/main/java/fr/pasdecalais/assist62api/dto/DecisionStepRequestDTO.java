package fr.pasdecalais.assist62api.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO pour la création ou la mise à jour d'une étape de décision.
 */
public class DecisionStepRequestDTO {

    @NotBlank
    private String text;

    private String imageUrl;

    private boolean isFinal;

    // Getters and Setters

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
}
