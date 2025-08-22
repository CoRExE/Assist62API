package fr.pasdecalais.assist62api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Représente une catégorie dans le système.
 * Permet de gérer une hiérarchie de catégories (parent/enfants).
 */
@Entity
public class Category {

    /**
     * Identifiant unique de la catégorie.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom de la catégorie.
     * Doit être unique et non nul, entre 3 et 50 caractères.
     */
    @Column(nullable = false, unique = true)
    @NotNull(message = "Name is required")
    @Size(min=3, max=50, message = "Name must be between 3 and 50 characters")
    private String name;

    /**
     * Catégorie parente (null si racine).
     */
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    /**
     * Liste des catégories enfants.
     */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> children;

    // Getters et Setters

    /**
     * Retourne l'identifiant de la catégorie.
     * @return id de la catégorie
     */
    public Long getId() {
        return id;
    }

    /**
     * Retourne le nom de la catégorie.
     * @return nom de la catégorie
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne la catégorie parente.
     * @return catégorie parente
     */
    public Category getParent() {
        return parent;
    }

    /**
     * Retourne la liste des enfants de la catégorie.
     * @return liste des enfants
     */
    public List<Category> getChildren() {
        return children;
    }

    /**
     * Définit le nom de la catégorie.
     * @param name nom à définir
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Définit la catégorie parente.
     * @param parent catégorie parente
     */
    public void setParent(Category parent) {
        this.parent = parent;
    }

    /**
     * Définit la liste des enfants et met à jour leur parent.
     * @param children liste des enfants
     */
    public void setChildren(List<Category> children) {
        this.children = children;
        for (Category child : children) {
            child.setParent(this);
        }
    }

    /**
     * Ajoute un enfant à la catégorie et met à jour son parent.
     * @param child enfant à ajouter
     */
    public void addChild(Category child) {
        this.children.add(child);
        child.setParent(this);
    }

    /**
     * Retire un enfant de la catégorie.
     * @param child enfant à retirer
     */
    public void removeChild(Category child) {
        this.children.remove(child);
    }
}