package fr.pasdecalais.assist62api.seeder;

import fr.pasdecalais.assist62api.factory.CategoryFactory;
import fr.pasdecalais.assist62api.model.Category;
import fr.pasdecalais.assist62api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Composant responsable de l'initialisation des catégories dans la base de données.
 * Utilise une fabrique pour générer des catégories et les persiste via le repository.
 */
@Component
public class CategorySeeder {
    private final CategoryFactory categoryFactory;
    private final CategoryRepository categoryRepository;

    /**
     * Constructeur injectant les dépendances nécessaires à la génération et la persistance des catégories.
     *
     * @param categoryFactory Fabrique pour créer des instances de Category
     * @param categoryRepository Repository pour persister les catégories
     */
    @Autowired
    public CategorySeeder(CategoryFactory categoryFactory, CategoryRepository categoryRepository) {
        this.categoryFactory = categoryFactory;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Génère et persiste une liste de catégories, avec une profondeur d'arborescence spécifiée.
     * Chaque catégorie peut avoir des enfants, créés récursivement selon la profondeur.
     *
     * @param numCategories Nombre de catégories à générer à chaque niveau
     * @param depth Profondeur de l'arborescence des catégories
     * @return Liste des catégories créées et persistées
     */
    public List<Category> seedCategories(int numCategories, int depth) {
        List<Category> categories = categoryRepository.saveAll(categoryFactory.createCategories(numCategories));
        if (depth > 0) {
            for (Category category : categories) {
                category.setChildren(seedCategories(numCategories, depth - 1));
                categoryRepository.save(category);
            }
        }
        return categories;
    }
}