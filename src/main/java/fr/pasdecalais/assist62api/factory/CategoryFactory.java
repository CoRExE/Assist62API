package fr.pasdecalais.assist62api.factory;

import com.github.javafaker.Faker;
import fr.pasdecalais.assist62api.model.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryFactory {

    private final Faker faker = new Faker();

    public Category createCategory() {
        Category category = new Category();
        category.setName(faker.internet().uuid());
        category.setChildren(new ArrayList<>());
        return category;
    }

    public List<Category> createCategories(int numberOfCategories) {
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < numberOfCategories; i++) {
            categories.add(createCategory());
        }
        return categories;
    }
}
