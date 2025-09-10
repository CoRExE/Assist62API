package fr.pasdecalais.assist62api.repository;

import fr.pasdecalais.assist62api.model.DecisionStep;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository pour l'entité {@link DecisionStep}.
 */
public interface DecisionStepRepository extends JpaRepository<DecisionStep, Long> {
}
