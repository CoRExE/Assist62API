package fr.pasdecalais.assist62api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // Indique que ce champ ne peut pas être nul en base
    @NotNull(message = "Name is required")
    @Size(min=3, max=50, message = "Name must be between 3 and 50 characters")
    private String name;

    @ManyToOne
    private Category parent;

    @OneToMany()
    private List<Category> children;

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getParent() {
        return parent;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
        for (Category child : children) {
            child.setParent(this);
        }
    }

    public void addChild(Category child) {
        this.children.add(child);
        child.setParent(this);
    }

    public void addChildren(List<Category> children) {
        for (Category child : children) {
            addChild(child);
        }
    }

    public void removeChild(Category child) {
        this.children.remove(child);
    }

    public void removeChildren(List<Category> children) {
        for (Category child : children) {
            removeChild(child);
        }
    }
}