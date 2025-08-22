package fr.pasdecalais.assist62api.service;

import fr.pasdecalais.assist62api.dto.CategoryRequestDTO;
import fr.pasdecalais.assist62api.dto.CategoryResponseDTO;
import fr.pasdecalais.assist62api.mapper.CategoryMapper;
import fr.pasdecalais.assist62api.model.Category;
import fr.pasdecalais.assist62api.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service pour la gestion des catégories.
 * Fournit des méthodes pour récupérer, créer, mettre à jour et supprimer des catégories.
 */
@Service
@Transactional
public class CategoryService {

    private static final String CATEGORY_NOT_FOUND_WITH_ID = "Category not found with id: ";

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    /**
     * Constructeur du service de catégorie.
     *
     * @param categoryRepository le repository de catégorie
     * @param categoryMapper le mapper pour convertir les entités en DTO
     */
    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    /**
     * Récupère toutes les catégories racines (sans parents).
     *
     * @return liste des DTO de catégories racines
     */
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getRootCategories() {
        return categoryRepository.findByParentIsNull().stream()
                .map(categoryMapper::toCategoryResponseDTO)
                .toList();
    }

    /**
     * Récupère toutes les catégories.
     *
     * @return liste des DTO de toutes les catégories
     */
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toCategoryResponseDTO)
                .toList();
    }

    /**
     * Récupère une catégorie par son identifiant.
     *
     * @param id identifiant de la catégorie
     * @return DTO de la catégorie
     * @throws EntityNotFoundException si la catégorie n'existe pas
     */
    @Transactional(readOnly = true)
    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND_WITH_ID + id));
        return categoryMapper.toCategoryResponseDTO(category);
    }

    /**
     * Récupère une catégorie par son nom.
     *
     * @param name nom de la catégorie
     * @return DTO de la catégorie
     * @throws EntityNotFoundException si la catégorie n'existe pas
     */
    @Transactional(readOnly = true)
    public CategoryResponseDTO getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with name: " + name));
        return categoryMapper.toCategoryResponseDTO(category);
    }

    /**
     * Récupère les enfants d'une catégorie par son identifiant.
     *
     * @param id identifiant de la catégorie
     * @return liste des DTO des catégories enfants
     * @throws EntityNotFoundException si la catégorie n'existe pas
     */
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> getChildrenByCategoryId(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND_WITH_ID + id));
        return category.getChildren().stream()
                .map(categoryMapper::toCategoryResponseDTO)
                .toList();
    }

    /**
     * Crée une nouvelle catégorie.
     *
     * @param categoryRequestDTO DTO contenant les informations de la catégorie à créer
     * @return DTO de la catégorie créée
     * @throws EntityNotFoundException si la catégorie parente n'existe pas
     */
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category();
        category.setName(categoryRequestDTO.getName());

        if (categoryRequestDTO.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(categoryRequestDTO.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent category not found with id: " + categoryRequestDTO.getParentId()));
            parentCategory.addChild(category);
        }

        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryResponseDTO(savedCategory);
    }

    /**
     * Met à jour une catégorie existante.
     *
     * @param id identifiant de la catégorie à mettre à jour
     * @param categoryRequestDTO DTO contenant les nouvelles informations
     * @return DTO de la catégorie mise à jour
     * @throws EntityNotFoundException si la catégorie ou la catégorie parente n'existe pas
     * @throws IllegalArgumentException si la catégorie est définie comme son propre parent
     */
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO categoryRequestDTO) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND_WITH_ID + id));

        existingCategory.setName(categoryRequestDTO.getName());

        if (categoryRequestDTO.getParentId() != null) {
            if (id.equals(categoryRequestDTO.getParentId())) {
                throw new IllegalArgumentException("A category cannot be its own parent.");
            }
            Category parentCategory = categoryRepository.findById(categoryRequestDTO.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent category not found with id: " + categoryRequestDTO.getParentId()));
            existingCategory.setParent(parentCategory);
        } else {
            existingCategory.setParent(null);
        }

        Category updatedCategory = categoryRepository.save(existingCategory);
        return categoryMapper.toCategoryResponseDTO(updatedCategory);
    }

    /**
     * Supprime une catégorie en toute sécurité (si elle n'a pas d'enfants).
     *
     * @param id identifiant de la catégorie à supprimer
     * @throws EntityNotFoundException si la catégorie n'existe pas
     * @throws IllegalStateException si la catégorie a des enfants
     */
    public void deleteSafelyCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND_WITH_ID + id));

        if (category.getChildren() != null && !category.getChildren().isEmpty()) {
            throw new IllegalStateException("Cannot delete category with children. Use cascade delete instead.");
        }

        categoryRepository.delete(category);
    }

    /**
     * Supprime une catégorie et ses enfants grâce au cascade JPA.
     *
     * @param id identifiant de la catégorie à supprimer
     */
    public void deleteCascadeCategory(Long id) {
        // Grâce à `cascade = CascadeType.ALL` sur l'entité, JPA gère la suppression des enfants automatiquement.
        categoryRepository.deleteById(id);
    }
}