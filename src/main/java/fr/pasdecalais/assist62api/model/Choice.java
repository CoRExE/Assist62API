package fr.pasdecalais.assist62api.model;

import jakarta.persistence.*;

/**
 * Représente un choix qu'un utilisateur peut faire à une étape de décision.
 * Un choix peut mener à une autre étape.
 */
@Entity
@Table(name = "choices")
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decision_step_id", nullable = false)
    private DecisionStep decisionStep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "next_step_id")
    private DecisionStep nextStep;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public DecisionStep getDecisionStep() {
        return decisionStep;
    }

    public void setDecisionStep(DecisionStep decisionStep) {
        this.decisionStep = decisionStep;
    }

    public DecisionStep getNextStep() {
        return nextStep;
    }

    public void setNextStep(DecisionStep nextStep) {
        this.nextStep = nextStep;
    }
}
