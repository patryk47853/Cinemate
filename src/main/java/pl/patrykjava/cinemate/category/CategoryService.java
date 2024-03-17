package pl.patrykjava.cinemate.category;

import org.springframework.stereotype.Service;
import pl.patrykjava.cinemate.exception.DuplicateResourceException;
import pl.patrykjava.cinemate.exception.RequestValidationException;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;
import pl.patrykjava.cinemate.movie.Movie;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public Category getCategoryById(Long id) {
        return categoryDao.selectCategoryById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No category with ID: " + id + " has been found."));
    }

    public Category getCategoryByName(String name) {
        return categoryDao.selectCategoryByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("No category named: " + name + " has been found."));
    }

    public List<Category> findAllCategories() {
        return categoryDao.selectAllCategories();
    }

    public void addCategory(CategoryAddRequest request) {
        String name = request.name();

        if (categoryDao.existsCategoryWithName(name)) {
            throw new DuplicateResourceException("Category: " + name + " is already in use.");
        }

        Category category = new Category(request.name(), new ArrayList<>());

        categoryDao.insertCategory(category);
    }

    public void deleteCategoryById(Long id) {
        if (!categoryDao.existsCategoryWithId(id))
            throw new ResourceNotFoundException("Category with ID: " + id + " not found.");

        categoryDao.deleteCategoryById(id);
    }

    public void deleteCategoryByName(String name) {
        if (!categoryDao.existsCategoryWithName(name))
            throw new ResourceNotFoundException("Category named: " + name + " not found.");

        categoryDao.deleteCategoryByName(name);
    }

    public void updateCategory(Long id, CategoryUpdateRequest request) {
        Category category = categoryDao.selectCategoryById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No category with such ID: " + id + " has been found."));

        boolean changed = false;

        if (request.name() != null && !request.name().equals(category.getCategoryName())) {
            if (categoryDao.existsCategoryWithName(request.name())) {
                throw new DuplicateResourceException("Category name: " + request.name() + " is already in use.");
            }
            category.setCategoryName(request.name());
            changed = true;
        }

        if (request.moviesToAdd() != null) {
            List<Movie> mainMovies = category.getMovies();
            List<Movie> moviesToAdd = request.moviesToAdd();

            for (Movie movieToAdd : moviesToAdd) {
                boolean alreadyExists = mainMovies.stream()
                        .anyMatch(existingMovie ->
                                existingMovie.getTitle().equals(movieToAdd.getTitle()) &&
                                        existingMovie.getDirector().equals(movieToAdd.getDirector()));

                if (alreadyExists) {
                    throw new DuplicateResourceException("Movie " + movieToAdd.getTitle() +
                            "directed by " + movieToAdd.getDirector() + " already exists in this category.");
                } else {
                    mainMovies.add(movieToAdd);
                }
            }

            category.setMovies(mainMovies);
            changed = true;
        }

        if (!changed) throw new RequestValidationException("No changes were made.");

        categoryDao.updateCategory(category);
    }
}
