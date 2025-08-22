package fr.pasdecalais.assist62api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO pour la création ou la mise à jour d'une catégorie.
 * Cette classe contient les données envoyées par le client.
 */
public class CategoryRequestDTO {

    /**
     * Nom de la catégorie.
     * Doit être renseigné (non nul) et comporter entre 3 et 50 caractères.
     */
    @NotNull(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    /**
     * Identifiant du parent de la catégorie (optionnel).
     */
    private Long parentId;

    /**
     * Retourne le nom de la catégorie.
     * @return le nom de la catégorie
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom de la catégorie.
     * @param name le nom à définir
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne l'identifiant du parent de la catégorie.
     * @return l'identifiant du parent
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * Définit l'identifiant du parent de la catégorie.
     * @param parentId l'identifiant du parent à définir
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}