package fr.pasdecalais.assist62api.seeder;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainSeeder {

    private final CategorySeeder categorySeeder;
    private final UserSeeder userSeeder;
    private final ProblemSeeder problemSeeder;
    private final NewsSeeder newsSeeder;

    @Autowired
    public MainSeeder(CategorySeeder categorySeeder, UserSeeder userSeeder, ProblemSeeder problemSeeder, NewsSeeder newsSeeder) {
        this.categorySeeder = categorySeeder;
        this.userSeeder = userSeeder;
        this.problemSeeder = problemSeeder;
        this.newsSeeder = newsSeeder;
    }

    @PostConstruct
    public void seed(){
        // Seed categories and users first as other entities depend on them
        if (categorySeeder != null) {
            categorySeeder.seedCategories(2, 3);
        }
        if (userSeeder != null) {
            userSeeder.seedUsers(10);
        }
        // Then seed problems
        if (problemSeeder != null) {
            problemSeeder.seedProblems(10, 3, 2);
        }
        // Finally, seed news
        if (newsSeeder != null) {
            newsSeeder.seedNews(15);
        }
    }
}
