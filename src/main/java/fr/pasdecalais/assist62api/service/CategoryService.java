package fr.pasdecalais.assist62api.service;

import fr.pasdecalais.assist62api.model.Category;
import fr.pasdecalais.assist62api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getRootCategories() {
        return categoryRepository.findByParentIsNull();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Category not found with name: " + name));
    }

    public List<Category> getChildrenByCategoryId(Long id) {
        Category category = getCategoryById(id);
        return category.getChildren();
    }

    public ResponseEntity<Void> createCategory(Category category, Optional<Long> parent) {
        if (parent.isPresent()) {
            Category parentCategory = getCategoryById(parent.get());
            category.setParent(parentCategory);
            parentCategory.addChild(category);
        }
        categoryRepository.save(category);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<Void> updateCategory(Long id, Category updatedCategory) {
        Category existingCategory = getCategoryById(id);
        existingCategory.setName(updatedCategory.getName());
        existingCategory.setParent(updatedCategory.getParent());
        categoryRepository.save(existingCategory);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteSafelyCategory(Long id) {
        Category category = getCategoryById(id);
        if (category.getChildren() != null && !category.getChildren().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        if (category.getParent() != null) {
            category.getParent().getChildren().remove(category);
        }
        categoryRepository.delete(category);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> deleteCascadeCategory(Long id) {
        Category category = getCategoryById(id);
        if (category.getChildren() != null && !category.getChildren().isEmpty()) {
            for (Category child : category.getChildren()) {
                deleteCascadeCategory(child.getId());
            }
        }
        if (category.getParent() != null) {
            category.getParent().getChildren().remove(category);
        }
        categoryRepository.delete(category);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}