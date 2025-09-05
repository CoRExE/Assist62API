package fr.pasdecalais.assist62api.service;

import fr.pasdecalais.assist62api.dto.ProblemRequestDTO;
import fr.pasdecalais.assist62api.dto.ProblemResponseDTO;
import fr.pasdecalais.assist62api.mapper.ProblemMapper;
import fr.pasdecalais.assist62api.model.Category;
import fr.pasdecalais.assist62api.model.Problem;
import fr.pasdecalais.assist62api.repository.CategoryRepository;
import fr.pasdecalais.assist62api.repository.ProblemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service pour la gestion des problèmes.
 */
@Service
@Transactional
public class ProblemService {

    private static final String PROBLEM_NOT_FOUND_WITH_ID = "Problem not found with id: ";
    private static final String CATEGORY_NOT_FOUND_WITH_ID = "Category not found with id: ";

    private final ProblemRepository problemRepository;
    private final CategoryRepository categoryRepository;
    private final ProblemMapper problemMapper;

    @Autowired
    public ProblemService(ProblemRepository problemRepository, CategoryRepository categoryRepository, ProblemMapper problemMapper) {
        this.problemRepository = problemRepository;
        this.categoryRepository = categoryRepository;
        this.problemMapper = problemMapper;
    }

    /**
     * Récupère tous les problèmes.
     * @return une liste de tous les problèmes
     */
    @Transactional(readOnly = true)
    public List<ProblemResponseDTO> getAllProblems() {
        return problemRepository.findAll().stream()
                .map(problemMapper::toProblemResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère les problèmes d'une catégorie spécifique.
     * @param categoryId l'identifiant de la catégorie
     * @return une liste de problèmes
     */
    @Transactional(readOnly = true)
    public List<ProblemResponseDTO> getProblemsByCategoryId(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException(CATEGORY_NOT_FOUND_WITH_ID + categoryId);
        }
        return problemRepository.findByCategoryId(categoryId).stream()
                .map(problemMapper::toProblemResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère un problème par son identifiant.
     * @param id l'identifiant du problème
     * @return le problème
     */
    @Transactional(readOnly = true)
    public ProblemResponseDTO getProblemById(Long id) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PROBLEM_NOT_FOUND_WITH_ID + id));
        return problemMapper.toProblemResponseDTO(problem);
    }

    /**
     * Récupère les problèmes correspondant à un titre spécifique.
     * @param title le titre du problème
     * @return une liste de problèmes
     */
    @Transactional(readOnly = true)
    public List<ProblemResponseDTO> getProblemsByTitle(String title) {
        return problemRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(problemMapper::toProblemResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Crée un nouveau problème.
     * @param problemRequestDTO les données du problème à créer
     * @return le problème créé
     */
    public ProblemResponseDTO createProblem(ProblemRequestDTO problemRequestDTO) {
        Category category = categoryRepository.findById(problemRequestDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND_WITH_ID + problemRequestDTO.getCategoryId()));

        Problem problem = new Problem();
        problem.setTitle(problemRequestDTO.getTitle());
        problem.setDescription(problemRequestDTO.getDescription());
        problem.setCategory(category);

        Problem savedProblem = problemRepository.save(problem);
        return problemMapper.toProblemResponseDTO(savedProblem);
    }

    /**
     * Met à jour un problème existant.
     * @param id l'identifiant du problème à mettre à jour
     * @param problemRequestDTO les nouvelles données du problème
     * @return le problème mis à jour
     */
    public ProblemResponseDTO updateProblem(Long id, ProblemRequestDTO problemRequestDTO) {
        Problem existingProblem = problemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PROBLEM_NOT_FOUND_WITH_ID + id));

        Category category = categoryRepository.findById(problemRequestDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND_WITH_ID + problemRequestDTO.getCategoryId()));

        existingProblem.setTitle(problemRequestDTO.getTitle());
        existingProblem.setDescription(problemRequestDTO.getDescription());
        existingProblem.setCategory(category);

        Problem updatedProblem = problemRepository.save(existingProblem);
        return problemMapper.toProblemResponseDTO(updatedProblem);
    }

    /**
     * Supprime un problème.
     * @param id l'identifiant du problème à supprimer
     */
    public void deleteProblem(Long id) {
        if (!problemRepository.existsById(id)) {
            throw new EntityNotFoundException(PROBLEM_NOT_FOUND_WITH_ID + id);
        }
        problemRepository.deleteById(id);
    }
}
