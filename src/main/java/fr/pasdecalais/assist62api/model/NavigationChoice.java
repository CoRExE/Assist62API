package fr.pasdecalais.assist62api.model;

import jakarta.persistence.*;

/**
 * Représente un choix qui mène à une autre étape de décision.
 */
@Entity
@DiscriminatorValue("NAVIGATION")
public class NavigationChoice extends Choice {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "next_step_id")
    private DecisionStep nextStep;

    // Getters and Setters

    public DecisionStep getNextStep() {
        return nextStep;
    }

    public void setNextStep(DecisionStep nextStep) {
        this.nextStep = nextStep;
    }
}
