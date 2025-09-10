package fr.pasdecalais.assist62api.dto;

/**
 * DTO représentant un problème dans les réponses de l'API.
 */
public class ProblemResponseDTO {

    private Long id;
    private String title;
    private String description;
    private Long categoryId;
    private String categoryName;
    private DecisionStepResponseDTO firstStep;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public DecisionStepResponseDTO getFirstStep() {
        return firstStep;
    }

    public void setFirstStep(DecisionStepResponseDTO firstStep) {
        this.firstStep = firstStep;
    }
}