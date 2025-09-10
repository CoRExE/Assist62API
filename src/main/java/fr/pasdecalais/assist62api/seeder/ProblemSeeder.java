package fr.pasdecalais.assist62api.seeder;

import fr.pasdecalais.assist62api.factory.ProblemFactory;
import fr.pasdecalais.assist62api.model.Category;
import fr.pasdecalais.assist62api.model.Problem;
import fr.pasdecalais.assist62api.repository.CategoryRepository;
import fr.pasdecalais.assist62api.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ProblemSeeder {

    private final ProblemFactory problemFactory;
    private final ProblemRepository problemRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProblemSeeder(ProblemFactory problemFactory, ProblemRepository problemRepository, CategoryRepository categoryRepository) {
        this.problemFactory = problemFactory;
        this.problemRepository = problemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void seedProblems(int numberOfProblems, int depth, int choicesPerStep) {
        if (problemRepository.count() > 0) {
            return; // Do not seed if problems already exist
        }
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            return;
        }

        List<Problem> problems = problemFactory.createProblems(numberOfProblems, categories, depth, choicesPerStep);

        problemRepository.saveAll(problems);
    }
}