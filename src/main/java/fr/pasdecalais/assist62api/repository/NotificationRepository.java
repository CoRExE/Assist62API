package fr.pasdecalais.assist62api.repository;

import fr.pasdecalais.assist62api.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository pour l'entité {@link Notification}.
 */
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Recherche une liste de notifications pour un utilisateur spécifique.
     *
     * @param userId L'identifiant de l'utilisateur.
     * @return Une liste de notifications pour l'utilisateur, triées par date de création descendante.
     */
    List<Notification> findByUserIdOrderByCreationDateDesc(Long userId);
}
