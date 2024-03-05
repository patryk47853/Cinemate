package pl.patrykjava.cinemate.category;

import org.springframework.stereotype.Service;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No category with ID: " + id + " has been found."));
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }
}
