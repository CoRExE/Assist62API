package fr.pasdecalais.assist62api.factory;

import com.github.javafaker.Faker;
import fr.pasdecalais.assist62api.model.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory pour la création d'instances de {@link Category}.
 * Utilise la librairie Faker pour générer des données fictives.
 */
@Component
public class CategoryFactory {

    /** Générateur de données fictives. */
    private final Faker faker = new Faker();

    /**
     * Crée une nouvelle instance de {@link Category} avec un nom aléatoire et une liste d'enfants vide.
     *
     * @return une nouvelle catégorie
     */
    public Category createCategory() {
        Category category = new Category();
        category.setName(faker.internet().uuid());
        category.setChildren(new ArrayList<>());
        return category;
    }

    /**
     * Crée une liste de catégories.
     *
     * @param numberOfCategories le nombre de catégories à créer
     * @return une liste de catégories
     */
    public List<Category> createCategories(int numberOfCategories) {
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < numberOfCategories; i++) {
            categories.add(createCategory());
        }
        return categories;
    }
}