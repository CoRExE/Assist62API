package fr.pasdecalais.assist62api.repository;

import fr.pasdecalais.assist62api.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository pour l'entité {@link Choice}.
 */
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
}
