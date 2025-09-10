package fr.pasdecalais.assist62api.mapper;

import fr.pasdecalais.assist62api.dto.ProblemResponseDTO;
import fr.pasdecalais.assist62api.model.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper pour convertir les entités {@link Problem} en objets {@link ProblemResponseDTO}.
 */
@Component
public class ProblemMapper {

    private final DecisionStepMapper decisionStepMapper;

    @Autowired
    public ProblemMapper(DecisionStepMapper decisionStepMapper) {
        this.decisionStepMapper = decisionStepMapper;
    }

    /**
     * Convertit une entité {@link Problem} en {@link ProblemResponseDTO}.
     *
     * @param problem l'entité Problem à convertir
     * @return le DTO correspondant, ou {@code null} si l'entité est {@code null}
     */
    public ProblemResponseDTO toProblemResponseDTO(Problem problem) {
        if (problem == null) {
            return null;
        }

        ProblemResponseDTO dto = new ProblemResponseDTO();
        dto.setId(problem.getId());
        dto.setTitle(problem.getTitle());
        dto.setDescription(problem.getDescription());
        

        if (problem.getCategory() != null) {
            dto.setCategoryId(problem.getCategory().getId());
            dto.setCategoryName(problem.getCategory().getName());
        }

        if (problem.getFirstStep() != null) {
            dto.setFirstStep(decisionStepMapper.toDecisionStepResponseDTO(problem.getFirstStep()));
        }

        return dto;
    }
}