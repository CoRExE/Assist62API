package fr.pasdecalais.assist62api.mapper;

import fr.pasdecalais.assist62api.dto.NotificationResponseDTO;
import fr.pasdecalais.assist62api.model.Notification;
import org.springframework.stereotype.Component;

/**
 * Mapper pour convertir les entités {@link Notification} en objets {@link NotificationResponseDTO}.
 */
@Component
public class NotificationMapper {

    /**
     * Convertit une entité {@link Notification} en {@link NotificationResponseDTO}.
     *
     * @param notification l'entité Notification à convertir
     * @return le DTO correspondant, ou {@code null} si l'entité est {@code null}
     */
    public NotificationResponseDTO toNotificationResponseDTO(Notification notification) {
        if (notification == null) {
            return null;
        }

        NotificationResponseDTO dto = new NotificationResponseDTO();
        dto.setId(notification.getId());
        dto.setMessage(notification.getMessage());
        dto.setCreationDate(notification.getCreationDate());
        dto.setRead(notification.isRead());

        if (notification.getNews() != null) {
            dto.setNewsId(notification.getNews().getId());
            dto.setNewsTitle(notification.getNews().getTitle());
        }

        return dto;
    }
}
