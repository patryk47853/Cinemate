package pl.patrykjava.cinemate.category;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryJPADataAccessService implements CategoryDao {

    private final CategoryRepository categoryRepository;

    public CategoryJPADataAccessService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Category> selectCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Optional<Category> selectCategoryByName(String name) {
        return categoryRepository.findByCategoryName(name);
    }

    @Override
    public boolean existsCategoryWithName(String name) {
        return categoryRepository.existsCategoryByCategoryName(name);
    }

    @Override
    public boolean existsCategoryWithId(Long id) {
        return categoryRepository.existsCategoryById(id);
    }

    @Override
    public List<Category> selectAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void insertCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void deleteCategoryByName(String name) {
        categoryRepository.deleteByCategoryName(name);
    }

    @Override
    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }
}
