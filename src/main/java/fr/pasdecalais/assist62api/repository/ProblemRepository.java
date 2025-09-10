package fr.pasdecalais.assist62api.repository;

import fr.pasdecalais.assist62api.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository pour l'entité {@link Problem}.
 */
public interface ProblemRepository extends JpaRepository<Problem, Long> {

    /**
     * Recherche une liste de problèmes par l'identifiant de leur catégorie.
     *
     * @param categoryId L'identifiant de la catégorie.
     * @return Une liste de problèmes appartenant à la catégorie spécifiée.
     */
    List<Problem> findByCategoryId(Long categoryId);
    List<Problem> findByTitleContainingIgnoreCase(String title);
}
