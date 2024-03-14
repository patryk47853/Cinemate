package pl.patrykjava.cinemate.category;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {
    Optional<Category> selectCategoryById(Long id);
    Optional<Category> selectCategoryByName(String name);
    List<Category> selectAllCategories();
    boolean existsCategoryWithName(String name);
    boolean existsCategoryWithId(Long id);
    void insertCategory(Category category);
    void deleteCategoryById(Long id);
    void deleteCategoryByName(String name);
    void updateCategory(Category category);
}
