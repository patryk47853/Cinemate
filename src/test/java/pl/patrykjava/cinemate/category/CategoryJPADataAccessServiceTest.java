package pl.patrykjava.cinemate.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryJPADataAccessServiceTest {

    private CategoryJPADataAccessService categoryJPADataAccessService;

    @Mock CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryJPADataAccessService = new CategoryJPADataAccessService(categoryRepository);
    }

    @Test
    void selectCategoryById() {
        //Given
        Long id = 1L;

        //When
        categoryJPADataAccessService.selectCategoryById(id);

        //Then
        verify(categoryRepository).findById(id);
    }

    @Test
    void selectCategoryByName() {
        //Given
        String name = "Drama";

        //When
        categoryJPADataAccessService.selectCategoryByName(name);

        //Then
        verify(categoryRepository).findByCategoryName(name);
    }

    @Test
    void existsCategoryWithName() {
        //Given
        String name = "Drama";

        //When
        categoryJPADataAccessService.existsCategoryWithName(name);

        //Then
        verify(categoryRepository).existsCategoryByCategoryName(name);
    }

    @Test
    void existsCategoryWithId() {
        //Given
        Long id = 1L;

        //When
        categoryJPADataAccessService.existsCategoryWithId(id);

        //Then
        verify(categoryRepository).existsCategoryById(id);
    }

    @Test
    void selectAllCategories() {
        //When
        categoryJPADataAccessService.selectAllCategories();

        //Then
        verify(categoryRepository).findAll();
    }

    @Test
    void insertCategory() {
        //Given
        Category category = new Category("Drama", new ArrayList<>());

        //When
        categoryJPADataAccessService.insertCategory(category);

        //Then
        verify(categoryRepository).save(category);
    }

    @Test
    void deleteCategoryById() {
        //Given
        Long id = 1L;

        //When
        categoryJPADataAccessService.deleteCategoryById(id);

        //Then
        verify(categoryRepository).deleteById(id);
    }

    @Test
    void deleteCategoryByName() {
        //Given
        String name = "Drama";

        //When
        categoryJPADataAccessService.deleteCategoryByName(name);

        //Then
        verify(categoryRepository).deleteByCategoryName(name);
    }

    @Test
    void updateCategory() {
        //Given
        Category category = new Category("Drama", new ArrayList<>());

        //When
        categoryJPADataAccessService.updateCategory(category);

        //Then
        verify(categoryRepository).save(category);
    }
}