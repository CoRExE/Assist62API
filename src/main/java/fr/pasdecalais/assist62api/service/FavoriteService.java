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
 * Service pour la gestion des favoris des utilisateurs.
 */
@Service
@Transactional
public class FavoriteService {

    private static final String USER_NOT_FOUND_WITH_ID = "User not found with id: ";
    private static final String CATEGORY_NOT_FOUND_WITH_ID = "Category not found with id: ";

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public FavoriteService(UserRepository userRepository, CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    /**
     * Ajoute une catégorie aux favoris d'un utilisateur.
     *
     * @param userId l'identifiant de l'utilisateur
     * @param categoryId l'identifiant de la catégorie à ajouter
     */
    public void addFavorite(Long userId, Long categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_ID + userId));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND_WITH_ID + categoryId));

        user.getFavorites().add(category);
        userRepository.save(user);
    }

    /**
     * Retire une catégorie des favoris d'un utilisateur.
     *
     * @param userId l'identifiant de l'utilisateur
     * @param categoryId l'identifiant de la catégorie à retirer
     */
    public void removeFavorite(Long userId, Long categoryId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_ID + userId));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND_WITH_ID + categoryId));

        user.getFavorites().remove(category);
        userRepository.save(user);
    }

    /**
     * Récupère la liste des catégories favorites d'un utilisateur.
     *
     * @param userId l'identifiant de l'utilisateur
     * @return une liste de DTO des catégories favorites
     */
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getFavorites(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_WITH_ID + userId));

        return user.getFavorites().stream()
                .map(categoryMapper::toCategoryResponseDTO)
                .toList();
    }
}
