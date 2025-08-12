package fr.pasdecalais.assist62api.repository;

import fr.pasdecalais.assist62api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentIsNull();

    Optional<Category> findByName(String name);
}