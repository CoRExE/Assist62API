package fr.pasdecalais.assist62api.seeder;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class MainSeeder {

    private final CategorySeeder categorySeeder;
    private final UserSeeder userSeeder;
    private final ProblemSeeder problemSeeder;
    private final NewsSeeder newsSeeder;

    private final Logger logger = Logger.getLogger(MainSeeder.class.getName());

    @Value("${app.branch.status}")
    private String branchStatus;

    @Autowired
    public MainSeeder(CategorySeeder categorySeeder, UserSeeder userSeeder, ProblemSeeder problemSeeder, NewsSeeder newsSeeder) {
        this.categorySeeder = categorySeeder;
        this.userSeeder = userSeeder;
        this.problemSeeder = problemSeeder;
        this.newsSeeder = newsSeeder;
    }

    @PostConstruct
    public void seed(){
        if (branchStatus.equals("dev")) {
            logger.log(Level.INFO, "Development configuration detected. Starting seeding process...");
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
            logger.log(Level.INFO, "Seeding process completed.");
        } else {
            logger.log(Level.INFO, "Production configuration detected. Skipping seeding process.");
        }
    }
}
