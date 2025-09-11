package fr.pasdecalais.assist62api.service;

import fr.pasdecalais.assist62api.dto.NewsRequestDTO;
import fr.pasdecalais.assist62api.dto.NewsResponseDTO;
import fr.pasdecalais.assist62api.mapper.NewsMapper;
import fr.pasdecalais.assist62api.model.Category;
import fr.pasdecalais.assist62api.model.News;
import fr.pasdecalais.assist62api.model.User;
import fr.pasdecalais.assist62api.repository.CategoryRepository;
import fr.pasdecalais.assist62api.repository.NewsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class NewsService {

    private static final String NEWS_NOT_FOUND_WITH_ID = "News not found with id: ";
    private static final String CATEGORY_NOT_FOUND_WITH_ID = "Category not found with id: ";

    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;
    private final NewsMapper newsMapper;
    private final NotificationService notificationService;

    @Autowired
    public NewsService(NewsRepository newsRepository, CategoryRepository categoryRepository, NewsMapper newsMapper, NotificationService notificationService) {
        this.newsRepository = newsRepository;
        this.categoryRepository = categoryRepository;
        this.newsMapper = newsMapper;
        this.notificationService = notificationService;
    }

    @Transactional(readOnly = true)
    public List<NewsResponseDTO> getAllNews() {
        return newsRepository.findAll().stream()
                .map(newsMapper::toNewsResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public NewsResponseDTO getNewsById(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NEWS_NOT_FOUND_WITH_ID + id));
        return newsMapper.toNewsResponseDTO(news);
    }

    public NewsResponseDTO createNews(NewsRequestDTO requestDTO) {
        User author = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Category category = null;
        if (requestDTO.getCategoryId() != null) {
            category = categoryRepository.findById(requestDTO.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND_WITH_ID + requestDTO.getCategoryId()));
        }

        News news = new News();
        news.setTitle(requestDTO.getTitle());
        news.setContent(requestDTO.getContent());
        news.setType(requestDTO.getType());
        news.setAuthor(author);
        news.setCategory(category);
        news.setCreationDate(LocalDateTime.now());

        News savedNews = newsRepository.save(news);

        // Trigger notification creation
        notificationService.createNotificationsForNews(savedNews);

        return newsMapper.toNewsResponseDTO(savedNews);
    }

    public NewsResponseDTO updateNews(Long id, NewsRequestDTO requestDTO) {
        News existingNews = newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NEWS_NOT_FOUND_WITH_ID + id));

        Category category = null;
        if (requestDTO.getCategoryId() != null) {
            category = categoryRepository.findById(requestDTO.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND_WITH_ID + requestDTO.getCategoryId()));
        }

        existingNews.setTitle(requestDTO.getTitle());
        existingNews.setContent(requestDTO.getContent());
        existingNews.setType(requestDTO.getType());
        existingNews.setCategory(category);

        News updatedNews = newsRepository.save(existingNews);
        return newsMapper.toNewsResponseDTO(updatedNews);
    }

    public void deleteNews(Long id) {
        if (!newsRepository.existsById(id)) {
            throw new EntityNotFoundException(NEWS_NOT_FOUND_WITH_ID + id);
        }
        newsRepository.deleteById(id);
    }
}