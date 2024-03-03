package pl.patrykjava.cinemate.service;

import org.springframework.stereotype.Service;
import pl.patrykjava.cinemate.entity.Actor;
import pl.patrykjava.cinemate.entity.Category;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;
import pl.patrykjava.cinemate.repository.ActorRepository;
import pl.patrykjava.cinemate.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No category with ID: " + id + " has been found."));
    }
}
