package fr.pasdecalais.assist62api.controller;

import fr.pasdecalais.assist62api.model.Category;
import fr.pasdecalais.assist62api.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Indique que cette classe est un contrôleur REST (elle renverra du JSON)
@RequestMapping("/api/category") // Toutes les URL de ce contrôleur commenceront par /api/categories
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Methods GET
    @GetMapping
    public List<Category> getRootCategories() {
        return categoryService.getRootCategories();
    }

    @GetMapping("/all")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/{name}")
    public Category getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }

    @GetMapping("/children/{id}")
    public List<Category> getChildrenByCategoryId(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return category.getChildren().stream().toList();
    }

    @GetMapping("/parent/{id}")
    public Category getParentByCategoryId(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return category.getParent() != null ? category.getParent() : null; // Retourne null si pas de parent
    }

    @PostMapping("/add")
    public ResponseEntity<Void> createCategory(@Valid @RequestBody Category category, @RequestParam(required = false) Long parentId) {
        return categoryService.createCategory(category, Optional.ofNullable(parentId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable Long id, @Valid @RequestBody Category category) {
        return categoryService.updateCategory(id, category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSafelyCategory(@PathVariable Long id) {
        return categoryService.deleteSafelyCategory(id);
    }

    @DeleteMapping("/{id}/force")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCascadeCategory(id);
    }
}