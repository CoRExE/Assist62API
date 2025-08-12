package fr.pasdecalais.assist62api.seeder;

import fr.pasdecalais.assist62api.factory.CategoryFactory;
import fr.pasdecalais.assist62api.model.Category;
import fr.pasdecalais.assist62api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategorySeeder {
    private final CategoryFactory categoryFactory;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategorySeeder(CategoryFactory categoryFactory, CategoryRepository categoryRepository) {
        this.categoryFactory = categoryFactory;
        this.categoryRepository = categoryRepository;
    }

    public List<Category> seedCategories(int numCategories, int depth) {
        List<Category> categories = categoryRepository.saveAllAndFlush(categoryFactory.createCategories(numCategories));
        for (Category category : categories) {
            if (depth > 0) {
                List<Category> subcategories = categoryRepository.saveAllAndFlush(seedCategories(numCategories, depth - 1));
                category.setChildren(subcategories);
            }
        }
        return categories;
    }
}
