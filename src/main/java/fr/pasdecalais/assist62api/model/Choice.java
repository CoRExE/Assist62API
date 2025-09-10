package fr.pasdecalais.assist62api.model;

import jakarta.persistence.*;

/**
 * Représente un choix abstrait qu'un utilisateur peut faire à une étape de décision.
 * Cette classe sert de base pour différents types de choix concrets.
 */
@Entity
@Table(name = "choices")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "choice_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decision_step_id", nullable = false)
    private DecisionStep decisionStep;

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
}
