package fr.pasdecalais.assist62api.dto;

import fr.pasdecalais.assist62api.model.Role;

/**
 * DTO pour représenter un utilisateur dans les réponses de l'API.
 * Cette classe expose les données publiques d'un utilisateur.
 */
public class UserResponseDTO {

    /**
     * L'identifiant unique de l'utilisateur.
     */
    private Long id;

    /**
     * Le nom de l'utilisateur.
     */
    private String name;

    /**
     * L'adresse e-mail de l'utilisateur.
     */
    private String email;

    /**
     * Le rôle de l'utilisateur (USER, MODO, ADMIN).
     */
    private Role role;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
