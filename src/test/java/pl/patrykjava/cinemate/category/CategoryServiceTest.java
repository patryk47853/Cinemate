package pl.patrykjava.cinemate.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.patrykjava.cinemate.exception.DuplicateResourceException;
import pl.patrykjava.cinemate.exception.RequestValidationException;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    private CategoryService categoryService;

    @Mock
    private CategoryDao categoryDao;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(categoryDao);
    }

    @Test
    void getCategoryById() {
        //Given
        Long id = 1L;

        Category category = new Category(
                id, "Drama", new ArrayList<>()
        );

        when(categoryDao.selectCategoryById(id)).thenReturn(Optional.of(category));

        //When
        Category actual = categoryService.getCategoryById(id);

        //Then
        assertThat(actual).isEqualTo(category);
    }

    @Test
    void willThrowWhenGetCategoryByIdReturnsEmptyOptional() {
        //Given
        Long id = 1L;

        when(categoryDao.selectCategoryById(id)).thenReturn(Optional.empty());

        //Then
        assertThatThrownBy(() -> categoryService.getCategoryById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No category with ID: " + id + " has been found.");
    }

    @Test
    void getCategoryByName() {
        //Given
        String name = "Drama";

        Category category = new Category(
                name, new ArrayList<>()
        );

        when(categoryDao.selectCategoryByName(name)).thenReturn(Optional.of(category));

        //When
        Category actual = categoryService.getCategoryByName(name);

        //Then
        assertThat(actual).isEqualTo(category);
    }

    @Test
    void willThrowWhenGetCategoryByNameReturnsEmptyOptional() {
        //Given
        String name = "Drama";

        when(categoryDao.selectCategoryByName(name)).thenReturn(Optional.empty());

        //Then
        assertThatThrownBy(() -> categoryService.getCategoryByName(name))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No category named: " + name + " has been found.");
    }

    @Test
    void findAllCategories() {
        //When
        categoryService.findAllCategories();

        //Then
        verify(categoryDao).selectAllCategories();
    }

    @Test
    void addCategory() {
        //Given
        String name = "Drama";

        when(categoryDao.existsCategoryWithName(name)).thenReturn(false);

        CategoryAddRequest request = new CategoryAddRequest(
                name
        );
        //When
        categoryService.addCategory(request);

        //Then
        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);

        verify(categoryDao).insertCategory(categoryArgumentCaptor.capture());

        Category actual = categoryArgumentCaptor.getValue();

        assertThat(actual.getId()).isNull();
        assertThat(actual.getCategoryName()).isEqualTo(request.name());
        assertThat(actual.getMovies()).isEmpty();
    }

    @Test
    void willThrowWhenAddCategoryWithExistingName() {
        //Given
        String name = "Drama";

        when(categoryDao.existsCategoryWithName(name)).thenReturn(true);

        CategoryAddRequest request = new CategoryAddRequest(
                name
        );
        //When
        assertThatThrownBy(() -> categoryService.addCategory(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Category: " + name + " is already in use.");

        //Then
        verify(categoryDao, never()).insertCategory(any());
    }

    @Test
    void deleteCategoryById() {
        //Given
        Long id = 1L;

        when(categoryDao.existsCategoryWithId(id)).thenReturn(true);
        //When
        categoryService.deleteCategoryById(id);

        //Then
        verify(categoryDao).deleteCategoryById(id);
    }

    @Test
    void willThrowWhenDeleteCategoryByIdDoesNotExists() {
        //Given
        Long id = 1L;

        when(categoryDao.existsCategoryWithId(id)).thenReturn(false);
        //When
        assertThatThrownBy(() -> categoryService.deleteCategoryById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Category with ID: " + id + " not found.");

        //Then
        verify(categoryDao, never()).deleteCategoryById(any());
    }

    @Test
    void deleteCategoryByName() {
        String name = "Drama";

        when(categoryDao.existsCategoryWithName(name)).thenReturn(true);
        //When
        categoryService.deleteCategoryByName(name);

        //Then
        verify(categoryDao).deleteCategoryByName(name);
    }

    @Test
    void willThrowWhenDeleteCategoryByNameDoesNotExists() {
        //Given
        String name = "Drama";

        when(categoryDao.existsCategoryWithName(name)).thenReturn(false);
        //When
        assertThatThrownBy(() -> categoryService.deleteCategoryByName(name))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Category named: " + name + " not found.");

        //Then
        verify(categoryDao, never()).deleteCategoryByName(name);
    }

    @Test
    void updateCategory() {
        //Given


        //When

        //Then
    }

    @Test
    void willThrowWhenUpdateNonExistingCategory() {
        //Given
//        Long id = -1L;
//
//        when(categoryDao.selectCategoryById(id)).thenReturn(Optional.empty());
        //When

        //Then
    }

    @Test
    void willThrowWhenUpdateCategoryWithExistingCategoryName() {
//        //Given
//        Long id = 1L;
//        String name = "Drama";
//        Category category = new Category(id, name, new ArrayList<>());
//
//        when(categoryDao.existsCategoryWithId(id)).thenReturn(true);
//
//        CategoryUpdateRequest request = new CategoryUpdateRequest(name);
//        //When
//        assertThatThrownBy(() -> categoryService.updateCategory(id, request))
//                .isInstanceOf(DuplicateResourceException.class)
//                .hasMessage("Category name: " + request.name() + " is already in use.");
//
//        //Then
//        verify(categoryDao, never()).updateCategory(any());
    }

    @Test
    void willThrowWhenUpdateCategoryWithoutChanges() {
        //Given
        Long id = 1L;
        Category category = new Category(id,"Drama", new ArrayList<>());

        when(categoryDao.selectCategoryById(id)).thenReturn(Optional.of(category));

        CategoryUpdateRequest request = new CategoryUpdateRequest(null);

        //When
        assertThatThrownBy(() -> categoryService.updateCategory(id, request))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("No changes were made.");

        //Then
        verify(categoryDao, never()).updateCategory(any());
    }
}