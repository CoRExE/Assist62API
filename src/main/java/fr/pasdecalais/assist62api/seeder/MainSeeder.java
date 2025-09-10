package fr.pasdecalais.assist62api.seeder;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainSeeder {
    // This class is intended to seed the database with initial data.
    // It can be used to create categories, subcategories, and other entities.
    // The actual implementation will depend on the specific requirements of the application.
    // This section is intended solely for demonstration, testing, and development purposes. (Dev)

    private final CategorySeeder categorySeeder;

    @Autowired
    public MainSeeder(CategorySeeder categorySeeder) {
        this.categorySeeder = categorySeeder;
    }

    @PostConstruct
    public void seed(){
        categorySeeder.seedCategories(2, 3);
    }
}
