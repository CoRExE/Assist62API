package fr.pasdecalais.assist62api.controller;

import fr.pasdecalais.assist62api.dto.UserRequestDTO;
import fr.pasdecalais.assist62api.dto.UserResponseDTO;
import fr.pasdecalais.assist62api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Contrôleur REST pour la gestion des utilisateurs.
 * Fournit des endpoints pour les opérations CRUD sur les utilisateurs.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    /**
     * Injection du service de gestion des utilisateurs.
     * @param userService le service utilisateur
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Récupère tous les utilisateurs.
     * @return liste de tous les utilisateurs
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Récupère un utilisateur par son identifiant.
     * @param id identifiant de l'utilisateur
     * @return l'utilisateur correspondant
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Récupère un utilisateur par son adresse e-mail.
     * @param email email de l'utilisateur
     * @return l'utilisateur correspondant
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    /**
     * Crée un nouvel utilisateur.
     * @param userRequestDTO données de l'utilisateur à créer
     * @return l'utilisateur créé
     */
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO createdUser = userService.createUser(userRequestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdUser);
    }

    /**
     * Met à jour un utilisateur existant.
     * @param id identifiant de l'utilisateur à mettre à jour
     * @param userRequestDTO nouvelles données de l'utilisateur
     * @return l'utilisateur mis à jour
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO updatedUser = userService.updateUser(id, userRequestDTO);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Supprime un utilisateur.
     * @param id identifiant de l'utilisateur à supprimer
     * @return réponse sans contenu
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
