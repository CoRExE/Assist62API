package fr.pasdecalais.assist62api.mapper;

import fr.pasdecalais.assist62api.dto.NewsResponseDTO;
import fr.pasdecalais.assist62api.model.News;
import org.springframework.stereotype.Component;

/**
 * Mapper pour convertir les entités {@link News} en objets {@link NewsResponseDTO}.
 */
@Component
public class NewsMapper {

    /**
     * Convertit une entité {@link News} en {@link NewsResponseDTO}.
     *
     * @param news l'entité News à convertir
     * @return le DTO correspondant, ou {@code null} si l'entité est {@code null}
     */
    public NewsResponseDTO toNewsResponseDTO(News news) {
        if (news == null) {
            return null;
        }

        NewsResponseDTO dto = new NewsResponseDTO();
        dto.setId(news.getId());
        dto.setTitle(news.getTitle());
        dto.setContent(news.getContent());
        dto.setType(news.getType());
        dto.setCreationDate(news.getCreationDate());

        if (news.getAuthor() != null) {
            dto.setAuthorId(news.getAuthor().getId());
            dto.setAuthorName(news.getAuthor().getName());
        }

        if (news.getCategory() != null) {
            dto.setCategoryId(news.getCategory().getId());
            dto.setCategoryName(news.getCategory().getName());
        }

        return dto;
    }
}
