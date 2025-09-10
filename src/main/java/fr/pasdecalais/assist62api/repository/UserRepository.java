package fr.pasdecalais.assist62api.repository;

import fr.pasdecalais.assist62api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository pour l'entité {@link User}.
 * Fournit des méthodes pour interagir avec la base de données concernant les utilisateurs.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Recherche un utilisateur par son adresse e-mail.
     * L'e-mail étant unique, cette méthode retourne au plus un utilisateur.
     *
     * @param email L'adresse e-mail de l'utilisateur à rechercher.
     * @return un {@link Optional} contenant l'utilisateur s'il est trouvé, sinon un {@link Optional} vide.
     */
    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);
}
