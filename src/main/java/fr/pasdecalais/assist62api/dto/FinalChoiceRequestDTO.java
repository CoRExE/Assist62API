package fr.pasdecalais.assist62api.dto;

/**
 * DTO pour les requêtes de création/mise à jour de choix de type FINAL.
 */
public class FinalChoiceRequestDTO extends ChoiceRequestDTO {

    private String conclusionText;

    // Getters and Setters

    public String getConclusionText() {
        return conclusionText;
    }

    public void setConclusionText(String conclusionText) {
        this.conclusionText = conclusionText;
    }
}
