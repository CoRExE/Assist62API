package fr.pasdecalais.assist62api.service;

import fr.pasdecalais.assist62api.dto.*;
import fr.pasdecalais.assist62api.mapper.ChoiceMapper;
import fr.pasdecalais.assist62api.model.*;
import fr.pasdecalais.assist62api.repository.ChoiceRepository;
import fr.pasdecalais.assist62api.repository.DecisionStepRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ChoiceService {

    private static final String CHOICE_NOT_FOUND_WITH_ID = "Choice not found with id: ";
    private static final String STEP_NOT_FOUND_WITH_ID = "DecisionStep not found with id: ";

    private final ChoiceRepository choiceRepository;
    private final DecisionStepRepository decisionStepRepository;
    private final ChoiceMapper choiceMapper;

    @Autowired
    public ChoiceService(ChoiceRepository choiceRepository, DecisionStepRepository decisionStepRepository, ChoiceMapper choiceMapper) {
        this.choiceRepository = choiceRepository;
        this.decisionStepRepository = decisionStepRepository;
        this.choiceMapper = choiceMapper;
    }

    @Transactional(readOnly = true)
    public ChoiceResponseDTO getChoiceById(Long id) {
        Choice choice = choiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CHOICE_NOT_FOUND_WITH_ID + id));
        return choiceMapper.toChoiceResponseDTO(choice);
    }

    @Transactional(readOnly = true)
    public List<ChoiceResponseDTO> getChoicesByType(String type) {
        Specification<Choice> spec = (root, query, criteriaBuilder) -> {
            Class<? extends Choice> choiceClass = switch (type.toUpperCase()) {
                case "NAVIGATION" -> NavigationChoice.class;
                case "URL" -> UrlChoice.class;
                case "FINAL" -> FinalChoice.class;
                default -> throw new IllegalArgumentException("Unknown choice type: " + type);
            };
            return criteriaBuilder.equal(root.type(), choiceClass);
        };
        List<Choice> choices = choiceRepository.findAll(spec);
        return choices.stream()
                .map(choiceMapper::toChoiceResponseDTO)
                .toList();
    }

    /**
     * Crée un nouveau choix.
     * @param requestDTO les données du choix à créer
     * @return le choix créé
     */
    public ChoiceResponseDTO createChoice(ChoiceRequestDTO requestDTO) {
        DecisionStep decisionStep = decisionStepRepository.findById(requestDTO.getDecisionStepId())
                .orElseThrow(() -> new EntityNotFoundException(STEP_NOT_FOUND_WITH_ID + requestDTO.getDecisionStepId()));

        Choice choice = switch (requestDTO) {
            case NavigationChoiceRequestDTO navigationChoiceRequestDTO ->
                    createNavigationChoice(navigationChoiceRequestDTO, decisionStep);
            case UrlChoiceRequestDTO urlChoiceRequestDTO -> createUrlChoice(urlChoiceRequestDTO, decisionStep);
            case FinalChoiceRequestDTO finalChoiceRequestDTO -> createFinalChoice(finalChoiceRequestDTO, decisionStep);
            default -> throw new IllegalArgumentException("Unknown ChoiceRequestDTO type");
        };

        Choice savedChoice = choiceRepository.save(choice);
        return choiceMapper.toChoiceResponseDTO(savedChoice);
    }

    public ChoiceResponseDTO updateChoice(Long id, ChoiceRequestDTO requestDTO) {
        Choice existingChoice = choiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CHOICE_NOT_FOUND_WITH_ID + id));

        existingChoice.setText(requestDTO.getText());

        switch (existingChoice) {
            case NavigationChoice navigationChoice when requestDTO instanceof NavigationChoiceRequestDTO ->
                    updateNavigationChoice(navigationChoice, (NavigationChoiceRequestDTO) requestDTO);
            case UrlChoice urlChoice when requestDTO instanceof UrlChoiceRequestDTO ->
                    updateUrlChoice(urlChoice, (UrlChoiceRequestDTO) requestDTO);
            case FinalChoice finalChoice when requestDTO instanceof FinalChoiceRequestDTO ->
                    updateFinalChoice(finalChoice, (FinalChoiceRequestDTO) requestDTO);
            default -> throw new IllegalArgumentException("Cannot change the type of a choice during update.");
        }

        Choice updatedChoice = choiceRepository.save(existingChoice);
        return choiceMapper.toChoiceResponseDTO(updatedChoice);
    }

    private NavigationChoice createNavigationChoice(NavigationChoiceRequestDTO dto, DecisionStep step) {
        NavigationChoice choice = new NavigationChoice();
        choice.setText(dto.getText());
        choice.setDecisionStep(step);
        if (dto.getNextStepId() != null) {
            DecisionStep nextStep = decisionStepRepository.findById(dto.getNextStepId())
                    .orElseThrow(() -> new EntityNotFoundException(STEP_NOT_FOUND_WITH_ID + dto.getNextStepId()));
            choice.setNextStep(nextStep);
        }
        return choice;
    }

    private UrlChoice createUrlChoice(UrlChoiceRequestDTO dto, DecisionStep step) {
        UrlChoice choice = new UrlChoice();
        choice.setText(dto.getText());
        choice.setDecisionStep(step);
        choice.setUrl(dto.getUrl());
        return choice;
    }

    private FinalChoice createFinalChoice(FinalChoiceRequestDTO dto, DecisionStep step) {
        FinalChoice choice = new FinalChoice();
        choice.setText(dto.getText());
        choice.setDecisionStep(step);
        choice.setConclusionText(dto.getConclusionText());
        return choice;
    }

    private void updateNavigationChoice(NavigationChoice choice, NavigationChoiceRequestDTO dto) {
        if (dto.getNextStepId() != null) {
            DecisionStep nextStep = decisionStepRepository.findById(dto.getNextStepId())
                    .orElseThrow(() -> new EntityNotFoundException(STEP_NOT_FOUND_WITH_ID + dto.getNextStepId()));
            choice.setNextStep(nextStep);
        } else {
            choice.setNextStep(null);
        }
    }

    private void updateUrlChoice(UrlChoice choice, UrlChoiceRequestDTO dto) {
        choice.setUrl(dto.getUrl());
    }

    private void updateFinalChoice(FinalChoice choice, FinalChoiceRequestDTO dto) {
        choice.setConclusionText(dto.getConclusionText());
    }

    public void deleteChoice(Long id) {
        if (!choiceRepository.existsById(id)) {
            throw new EntityNotFoundException(CHOICE_NOT_FOUND_WITH_ID + id);
        }
        choiceRepository.deleteById(id);
    }
}