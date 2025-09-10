package fr.pasdecalais.assist62api.dto;

/**
 * DTO pour les réponses de connexion.
 * Contient le token JWT généré après une authentification réussie.
 */
public class LoginResponseDTO {

    private String token;

    public LoginResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
