package fr.pasdecalais.assist62api.factory;

import com.github.javafaker.Faker;
import fr.pasdecalais.assist62api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProblemFactory {

    private final Faker faker = new Faker();
    private final DecisionStepFactory decisionStepFactory;
    private final ChoiceFactory choiceFactory;

    @Autowired
    public ProblemFactory(DecisionStepFactory decisionStepFactory, ChoiceFactory choiceFactory) {
        this.decisionStepFactory = decisionStepFactory;
        this.choiceFactory = choiceFactory;
    }

    public Problem createProblem(Category category, int depth, int choicesPerStep) {
        Problem problem = new Problem();
        problem.setTitle(faker.lorem().sentence(5));
        problem.setDescription(faker.lorem().paragraph());
        problem.setCategory(category);
        problem.setFirstStep(createDecisionStepTree(depth, choicesPerStep));
        return problem;
    }

    public List<Problem> createProblems(int numberOfProblems, List<Category> categories, int depth, int choicesPerStep) {
        List<Problem> problems = new ArrayList<>();
        if (categories.isEmpty()) {
            return problems;
        }
        for (int i = 0; i < numberOfProblems; i++) {
            Category randomCategory = categories.get(faker.random().nextInt(categories.size()));
            problems.add(createProblem(randomCategory, depth, choicesPerStep));
        }
        return problems;
    }

    private DecisionStep createDecisionStepTree(int depth, int choicesPerStep) {
        boolean isFinal = (depth <= 0);
        DecisionStep step = decisionStepFactory.createDecisionStep(isFinal);

        if (!isFinal) {
            List<Choice> choices = new ArrayList<>();
            for (int i = 0; i < choicesPerStep; i++) {
                Choice choice = choiceFactory.createChoice(step);
                if (choice instanceof NavigationChoice navigationChoice) {
                    DecisionStep nextStep = createDecisionStepTree(depth - 1, choicesPerStep);
                    navigationChoice.setNextStep(nextStep);
                }
                choices.add(choice);
            }
            step.setChoices(choices);
        }
        return step;
    }
}