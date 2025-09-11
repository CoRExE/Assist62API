package fr.pasdecalais.assist62api.controller;

import fr.pasdecalais.assist62api.dto.NotificationResponseDTO;
import fr.pasdecalais.assist62api.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des notifications.
 */
@RestController
@RequestMapping("/api")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Récupère toutes les notifications pour un utilisateur spécifique.
     * @param userId l'identifiant de l'utilisateur
     * @return la liste des notifications
     */
    @GetMapping("/user/{userId}/notifications")
    @PreAuthorize("hasAuthority('ADMIN') or #userId == authentication.principal.id")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getNotificationsForUser(userId));
    }

    /**
     * Marque une notification comme lue.
     * @param notificationId l'identifiant de la notification
     * @return une réponse sans contenu
     */
    @PostMapping("/notifications/{notificationId}/read")
    @PreAuthorize("@notificationService.getNotificationById(#notificationId).getUser().getId() == authentication.principal.id or hasAuthority('ADMIN')")
    public ResponseEntity<Void> markAsRead(@PathVariable Long notificationId) {
        notificationService.markNotificationAsRead(notificationId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Supprime une notification.
     * @param notificationId l'identifiant de la notification
     * @return une réponse sans contenu
     */
    @DeleteMapping("/notifications/{notificationId}")
    @PreAuthorize("@notificationService.getNotificationById(#notificationId).getUser().getId() == authentication.principal.id or hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.noContent().build();
    }
}
