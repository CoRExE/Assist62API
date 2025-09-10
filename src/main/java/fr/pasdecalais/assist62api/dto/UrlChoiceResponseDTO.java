package fr.pasdecalais.assist62api.dto;

/**
 * DTO pour les réponses de choix de type URL.
 */
public class UrlChoiceResponseDTO extends ChoiceResponseDTO {

    private String url;

    // Getters and Setters

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
