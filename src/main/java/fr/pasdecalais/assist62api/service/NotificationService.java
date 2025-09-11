package fr.pasdecalais.assist62api.service;

import fr.pasdecalais.assist62api.dto.NotificationResponseDTO;
import fr.pasdecalais.assist62api.mapper.NotificationMapper;
import fr.pasdecalais.assist62api.model.Category;
import fr.pasdecalais.assist62api.model.News;
import fr.pasdecalais.assist62api.model.Notification;
import fr.pasdecalais.assist62api.model.User;
import fr.pasdecalais.assist62api.repository.NotificationRepository;
import fr.pasdecalais.assist62api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationService {

    private static final String NOTIFICATION_NOT_FOUND_WITH_ID = "Notification not found with id: ";

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;

    private final NotificationService self;

    @Autowired
    public NotificationService(
            NotificationRepository notificationRepository,
            UserRepository userRepository,
            NotificationMapper notificationMapper,
            @Lazy NotificationService self
    ) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.notificationMapper = notificationMapper;
        this.self = self;
    }

    public void createNotificationsForNews(News news) {
        if (news.getCategory() == null) {
            return; // No category, no notifications
        }

        Set<Category> relevantCategories = getCategoryAndItsAncestors(news.getCategory());
        Set<Long> relevantCategoryIds = relevantCategories.stream().map(Category::getId).collect(Collectors.toSet());

        List<User> usersToNotify = userRepository.findAll().stream()
                .filter(user -> user.getSubscriptions().stream()
                        .anyMatch(subscription -> relevantCategoryIds.contains(subscription.getId())))
                .toList();

        for (User user : usersToNotify) {
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setNews(news);
            notification.setMessage("Nouvelle actualité dans la catégorie '" + news.getCategory().getName() + "': " + news.getTitle());
            notification.setCreationDate(LocalDateTime.now());
            notification.setRead(false);
            notificationRepository.save(notification);
        }
    }

    private Set<Category> getCategoryAndItsAncestors(Category category) {
        Set<Category> categories = new HashSet<>();
        Category current = category;
        while (current != null) {
            categories.add(current);
            current = current.getParent();
        }
        return categories;
    }

    @Transactional(readOnly = true)
    public List<NotificationResponseDTO> getNotificationsForUser(Long userId) {
        return notificationRepository.findByUserIdOrderByCreationDateDesc(userId).stream()
                .map(notificationMapper::toNotificationResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Notification getNotificationById(Long notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException(NOTIFICATION_NOT_FOUND_WITH_ID + notificationId));
    }

    public void markNotificationAsRead(Long notificationId) {
        Notification notification = self.getNotificationById(notificationId);
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    public void deleteNotification(Long notificationId) {
        if (!notificationRepository.existsById(notificationId)) {
            throw new EntityNotFoundException(NOTIFICATION_NOT_FOUND_WITH_ID + notificationId);
        }
        notificationRepository.deleteById(notificationId);
    }
}