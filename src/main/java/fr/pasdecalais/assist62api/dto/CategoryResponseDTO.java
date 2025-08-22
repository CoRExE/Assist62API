package fr.pasdecalais.assist62api.dto;

import java.util.List;

/**
 * DTO représentant une catégorie dans les réponses de l'API.
 * Cette structure évite la récursivité infinie grâce à l'utilisation d'une liste d'enfants.
 */
public class CategoryResponseDTO {

    /**
     * Identifiant unique de la catégorie.
     */
    private Long id;

    /**
     * Nom de la catégorie.
     */
    private String name;

    /**
     * Identifiant de la catégorie parente, si elle existe.
     */
    private Long parentId;

    /**
     * Liste des sous-catégories (enfants) de cette catégorie.
     */
    private List<CategoryResponseDTO> children;

    /**
     * Retourne l'identifiant de la catégorie.
     * @return id de la catégorie
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant de la catégorie.
     * @param id identifiant à définir
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retourne le nom de la catégorie.
     * @return nom de la catégorie
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom de la catégorie.
     * @param name nom à définir
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne l'identifiant de la catégorie parente.
     * @return id de la catégorie parente
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * Définit l'identifiant de la catégorie parente.
     * @param parentId identifiant de la catégorie parente à définir
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * Retourne la liste des enfants de la catégorie.
     * @return liste des sous-catégories
     */
    public List<CategoryResponseDTO> getChildren() {
        return children;
    }

    /**
     * Définit la liste des enfants de la catégorie.
     * @param children liste des sous-catégories à définir
     */
    public void setChildren(List<CategoryResponseDTO> children) {
        this.children = children;
    }
}