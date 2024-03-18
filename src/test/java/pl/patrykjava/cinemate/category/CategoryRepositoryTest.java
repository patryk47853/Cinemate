package pl.patrykjava.cinemate.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.patrykjava.cinemate.CategoryAbstractTestcontainers;

import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest extends CategoryAbstractTestcontainers {

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
    }

    @Test
    void existsCategoryByCategoryName() {
        //Given
        String name = FAKER.name().title() + UUID.randomUUID();

        Category category = new Category(name, new ArrayList<>());

        categoryRepository.save(category);

        //When
        var actual = categoryRepository.existsCategoryByCategoryName(name);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsCategoryByCategoryNameFailsWhenCategoryNameNotPresent() {
        //Given
        String name = FAKER.name().title() + UUID.randomUUID() + "-";

        //When
        var actual = categoryRepository.existsCategoryByCategoryName(name);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void findByCategoryName() {
        //Given
        String name = FAKER.name().title() + UUID.randomUUID();

        Category category = new Category(name, new ArrayList<>());

        categoryRepository.save(category);

        //When
        var actual = categoryRepository.findByCategoryName(name);

        //Then
        assertThat(actual).isPresent();
    }

    @Test
    void findByCategoryNameFailWhenCategoryNameNotPresent() {
        //Given
        String name = FAKER.name().title() + UUID.randomUUID();

        //When
        var actual = categoryRepository.findByCategoryName(name);

        //Then
        assertThat(actual).isEmpty();
    }

    @Test
    void deleteByCategoryName() {
        //Given
        String name = FAKER.name().title() + UUID.randomUUID();

        Category category = new Category(name, new ArrayList<>());

        categoryRepository.save(category);

        var isSaved = categoryRepository.findAll().stream()
                .anyMatch(c -> c.getCategoryName().equals(name));

        //When
        categoryRepository.deleteByCategoryName(name);

        var actual = categoryRepository.findAll().stream()
                .anyMatch(c -> c.getCategoryName().equals(name));

        //Then
        assertThat(isSaved).isTrue();
        assertThat(actual).isFalse();
    }

    @Test
    void existsCategoryById() {
        //Given
        String name = FAKER.name().title() + UUID.randomUUID();

        Category category = new Category(name, new ArrayList<>());

        categoryRepository.save(category);

        Long id = categoryRepository.findAll().stream()
                .filter(c -> c.getCategoryName().equals(name))
                .map(Category::getId)
                .findFirst()
                .orElseThrow();

        //When
        var actual = categoryRepository.existsCategoryById(id);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsCategoryByIdFailWhenIdNotPresent() {
        //Given
        Long id = -1L;

        //When
        var actual = categoryRepository.existsCategoryById(id);

        //Then
        assertThat(actual).isFalse();
    }
}