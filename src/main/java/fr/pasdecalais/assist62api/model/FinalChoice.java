package fr.pasdecalais.assist62api.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Représente un choix qui termine le parcours avec un message de conclusion.
 */
@Entity
@DiscriminatorValue("FINAL")
public class FinalChoice extends Choice {

    @Column(name = "conclusion_text", columnDefinition = "TEXT")
    private String conclusionText;

    // Getters and Setters

    public String getConclusionText() {
        return conclusionText;
    }

    public void setConclusionText(String conclusionText) {
        this.conclusionText = conclusionText;
    }
}
