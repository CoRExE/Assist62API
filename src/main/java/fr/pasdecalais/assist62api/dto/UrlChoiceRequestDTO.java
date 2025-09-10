package fr.pasdecalais.assist62api.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO pour les requêtes de création/mise à jour de choix de type URL.
 */
public class UrlChoiceRequestDTO extends ChoiceRequestDTO {

    @NotBlank
    private String url;

    // Getters and Setters

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
