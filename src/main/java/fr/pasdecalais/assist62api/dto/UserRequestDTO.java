package fr.pasdecalais.assist62api.dto;

import fr.pasdecalais.assist62api.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO pour la création ou la mise à jour d'un utilisateur.
 * Contient les données envoyées par le client avec des contraintes de validation.
 */
public class UserRequestDTO {

    /**
     * Le nom de l'utilisateur.
     * Doit être non vide et ne pas dépasser 100 caractères.
     */
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    /**
     * L'adresse e-mail de l'utilisateur.
     * Doit être une adresse e-mail valide et non vide.
     */
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    /**
     * Le mot de passe de l'utilisateur.
     * Doit être non vide et comporter au moins 8 caractères.
     */
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    /**
     * Le rôle de l'utilisateur.
     * Doit être spécifié.
     */
    @NotNull(message = "Role is required")
    private Role role;

    // Getters and Setters

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
