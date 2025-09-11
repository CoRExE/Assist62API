package fr.pasdecalais.assist62api.factory;

import com.github.javafaker.Faker;
import fr.pasdecalais.assist62api.model.Category;
import fr.pasdecalais.assist62api.model.News;
import fr.pasdecalais.assist62api.model.NewsType;
import fr.pasdecalais.assist62api.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class NewsFactory {

    private final Faker faker = new Faker();
    private final Random random = new Random();

    public News createNews(User author, Category category) {
        News news = new News();
        news.setTitle(faker.lorem().sentence(4, 3));
        news.setContent(faker.lorem().paragraphs(3).toString());
        news.setType(NewsType.values()[random.nextInt(NewsType.values().length)]);
        news.setCreationDate(LocalDateTime.now().minusDays(random.nextInt(30)));
        news.setAuthor(author);
        news.setCategory(category); // Can be null
        return news;
    }
}
