package fr.pasdecalais.assist62api.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * DTO de base abstrait pour les réponses de choix.
 * Utilise les annotations Jackson pour la gestion du polymorphisme.
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = NavigationChoiceResponseDTO.class, name = "NAVIGATION"),
    @JsonSubTypes.Type(value = UrlChoiceResponseDTO.class, name = "URL"),
    @JsonSubTypes.Type(value = FinalChoiceResponseDTO.class, name = "FINAL")
})
public abstract class ChoiceResponseDTO {

    private Long id;
    private String text;

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
}