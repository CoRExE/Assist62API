package fr.pasdecalais.assist62api.factory;

import com.github.javafaker.Faker;
import fr.pasdecalais.assist62api.model.*;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ChoiceFactory {

    private final Faker faker = new Faker();
    private final Random random = new Random();

    public Choice createChoice(DecisionStep decisionStep) {
        int choiceType = random.nextInt(3);
        Choice choice;
        switch (choiceType) {
            case 0:
                NavigationChoice navChoice = new NavigationChoice();
                navChoice.setText(faker.lorem().sentence(3));
                choice = navChoice;
                break;
            case 1:
                UrlChoice urlChoice = new UrlChoice();
                urlChoice.setText(faker.lorem().sentence(3));
                urlChoice.setUrl(faker.internet().url());
                choice = urlChoice;
                break;
            default:
                FinalChoice finalChoice = new FinalChoice();
                finalChoice.setText(faker.lorem().sentence(3));
                finalChoice.setConclusionText(faker.lorem().paragraph());
                choice = finalChoice;
                break;
        }
        choice.setDecisionStep(decisionStep);
        return choice;
    }
}
