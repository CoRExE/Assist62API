package fr.pasdecalais.assist62api.repository;

import fr.pasdecalais.assist62api.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository pour l'entité {@link News}.
 */
public interface NewsRepository extends JpaRepository<News, Long> {

    /**
     * Recherche une liste d'actualités par l'identifiant de leur catégorie.
     *
     * @param categoryId L'identifiant de la catégorie.
     * @return Une liste d'actualités appartenant à la catégorie spécifiée.
     */
    List<News> findByCategoryId(Long categoryId);

    /**
     * Recherche une liste d'actualités par l'identifiant de leur auteur.
     *
     * @param authorId L'identifiant de l'auteur.
     * @return Une liste d'actualités créées par l'auteur spécifié.
     */
    List<News> findByAuthorId(Long authorId);

}
