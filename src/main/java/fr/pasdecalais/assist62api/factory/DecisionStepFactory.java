package fr.pasdecalais.assist62api.factory;

import com.github.javafaker.Faker;
import fr.pasdecalais.assist62api.model.DecisionStep;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DecisionStepFactory {

    private final Faker faker = new Faker();

    public DecisionStep createDecisionStep(boolean isFinal) {
        DecisionStep decisionStep = new DecisionStep();
        decisionStep.setText(faker.lorem().paragraph());
        decisionStep.setImageUrl(faker.internet().image());
        decisionStep.setFinal(isFinal);
        if (!isFinal) {
            decisionStep.setChoices(new ArrayList<>());
        }
        return decisionStep;
    }
}
