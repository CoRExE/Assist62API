package fr.pasdecalais.assist62api.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO pour les requêtes de connexion.
 * Contient les identifiants nécessaires pour l'authentification.
 */
public class LoginRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Password is required")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}