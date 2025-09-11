package fr.pasdecalais.assist62api.service;

import fr.pasdecalais.assist62api.dto.CategoryResponseDTO;
import fr.pasdecalais.assist62api.mapper.CategoryMapper;
import fr.pasdecalais.assist62api.model.Category;
import fr.pasdecalais.assist62api.model.User;
import fr.pasdecalais.assist62api.repository.CategoryRepository;
import fr.pasdecalais.assist62api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service pour la gestion des abonnements des utilisateurs aux catégories.
 */
@Service
@Transactional
public class SubscriptionService {

    private static final String USER_NOT_FOUND_WITH_ID = "User not found with id: ";
    private static final String CATEGORY_NOT_FOUND_WITH_ID = "Category not found with id: ";

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public SubscriptionService(UserRepository userRepository, CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    /**
     * Abonne un utilisateur à une catégorie.
     *
     * @param userId l'identifiant de l'utilisateur
     * @param categoryId l'identifiant de la catégorie
     */
    public void addSubscription(Long userId, Long categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_ID + userId));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND_WITH_ID + categoryId));

        user.getSubscriptions().add(category);
        userRepository.save(user);
    }

    /**
     * Désabonne un utilisateur d'une catégorie.
     *
     * @param userId l'identifiant de l'utilisateur
     * @param categoryId l'identifiant de la catégorie
     */
    public void removeSubscription(Long userId, Long categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_ID + userId));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND_WITH_ID + categoryId));

        user.getSubscriptions().remove(category);
        userRepository.save(user);
    }

    /**
     * Récupère la liste des abonnements d'un utilisateur.
     *
     * @param userId l'identifiant de l'utilisateur
     * @return une liste de DTO des catégories auxquelles l'utilisateur est abonné
     */
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getSubscriptions(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_ID + userId));

        return user.getSubscriptions().stream()
                .map(categoryMapper::toCategoryResponseDTO)
                .toList();
    }
}
