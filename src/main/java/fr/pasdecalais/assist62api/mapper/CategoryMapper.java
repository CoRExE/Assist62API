package fr.pasdecalais.assist62api.mapper;

import fr.pasdecalais.assist62api.dto.CategoryResponseDTO;
import fr.pasdecalais.assist62api.model.Category;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Mapper pour convertir les entités {@link Category} en objets {@link CategoryResponseDTO}.
 */
@Component
public class CategoryMapper {

    /**
     * Mappe les champs de base d'une entité {@link Category} vers un {@link CategoryResponseDTO}.
     *
     * @param category l'entité Category à mapper
     * @return un DTO avec les champs de base renseignés
     */
    private CategoryResponseDTO mapBasicFields(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        if (category.getParent() != null) {
            dto.setParentId(category.getParent().getId());
        }
        return dto;
    }

    /**
     * Convertit une entité {@link Category} en {@link CategoryResponseDTO} complet,
     * incluant ses enfants.
     *
     * @param category l'entité Category à convertir
     * @return le DTO correspondant, ou {@code null} si l'entité est {@code null}
     */
    public CategoryResponseDTO toCategoryResponseDTO(Category category) {
        if (category == null) {
            return null;
        }
        CategoryResponseDTO dto = mapBasicFields(category);
        if (category.getChildren() != null && !category.getChildren().isEmpty()) {
            dto.setChildren(
                    category.getChildren().stream()
                            .map(this::toSimpleCategoryResponseDTO)
                            .toList()
            );
        } else {
            dto.setChildren(Collections.emptyList());
        }
        return dto;
    }

    /**
     * Convertit une entité {@link Category} en {@link CategoryResponseDTO} simplifié,
     * sans enfants.
     *
     * @param category l'entité Category à convertir
     * @return le DTO simplifié, ou {@code null} si l'entité est {@code null}
     */
    public CategoryResponseDTO toSimpleCategoryResponseDTO(Category category) {
        if (category == null) {
            return null;
        }
        CategoryResponseDTO dto = mapBasicFields(category);
        dto.setChildren(Collections.emptyList());
        return dto;
    }
}