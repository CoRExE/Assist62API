package fr.pasdecalais.assist62api.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Représente un choix qui redirige vers une URL.
 */
@Entity
@DiscriminatorValue("URL")
public class UrlChoice extends Choice {

    @Column(name = "url")
    private String url;

    // Getters and Setters

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
