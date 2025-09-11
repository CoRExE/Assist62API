package fr.pasdecalais.assist62api.seeder;

import fr.pasdecalais.assist62api.factory.NewsFactory;
import fr.pasdecalais.assist62api.model.Category;
import fr.pasdecalais.assist62api.model.News;
import fr.pasdecalais.assist62api.model.User;
import fr.pasdecalais.assist62api.repository.CategoryRepository;
import fr.pasdecalais.assist62api.repository.NewsRepository;
import fr.pasdecalais.assist62api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class NewsSeeder {

    private final NewsFactory newsFactory;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final Logger logger = Logger.getLogger(NewsSeeder.class.getName());
    private final Random random = new Random();

    @Autowired
    public NewsSeeder(NewsFactory newsFactory, NewsRepository newsRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.newsFactory = newsFactory;
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public void seedNews(int numberOfNews) {
        List<User> users = userRepository.findAll();
        List<Category> categories = categoryRepository.findAll();

        if (users.isEmpty()) {
            logger.log(Level.WARNING,"Cannot seed news because no users exist.");
            return;
        }

        for (int i = 0; i < numberOfNews; i++) {
            User randomAuthor = users.get(random.nextInt(users.size()));
            Category randomCategory = categories.isEmpty() ? null : categories.get(random.nextInt(categories.size()));

            News news = newsFactory.createNews(randomAuthor, randomCategory);
            newsRepository.save(news);
        }
    }
}
