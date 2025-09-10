package fr.pasdecalais.assist62api.dto;

/**
 * DTO pour les réponses de choix de type FINAL.
 */
public class FinalChoiceResponseDTO extends ChoiceResponseDTO {

    private String conclusionText;

    // Getters and Setters

    public String getConclusionText() {
        return conclusionText;
    }

    public void setConclusionText(String conclusionText) {
        this.conclusionText = conclusionText;
    }
}
