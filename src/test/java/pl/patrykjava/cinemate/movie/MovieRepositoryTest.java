package pl.patrykjava.cinemate.movie;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.patrykjava.cinemate.actor.Actor;
import pl.patrykjava.cinemate.actor.ActorRepository;
import pl.patrykjava.cinemate.category.Category;
import pl.patrykjava.cinemate.category.CategoryRepository;
import pl.patrykjava.cinemate.director.Director;
import pl.patrykjava.cinemate.director.DirectorRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MovieRepositoryTest extends MovieAbstractTestcontainers {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        movieRepository.deleteAll();
        directorRepository.deleteAll();
        actorRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void existsMovieByTitle() {
        //Given
        String title = "Interstellar";

        Movie movie = new Movie(1L, title);

        movieRepository.save(movie);

        //When
        var actual = movieRepository.existsMovieByTitle(title);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsMovieByTitleFailsWhenTitleNotPresent() {
        //Given
        String title = "Interstellar";

        //When
        var actual = movieRepository.existsMovieByTitle(title);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void existsMovieByDirectorId() {
        //Given
        String title = "Interstellar";
        Director director = new Director("Christopher", "Nolan");

        directorRepository.save(director);

        Long id = directorRepository.findAll()
                .stream()
                .filter(d -> d.getFirstName().equals("Christopher") && d.getLastName().equals("Nolan"))
                .map(Director::getId)
                .findFirst()
                .orElseThrow();

        Movie movie = new Movie(title, director);

        movieRepository.save(movie);

        //When
        var actual = movieRepository.existsMovieByDirectorId(id);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsMovieByDirectorIdFailsWhenDirectorNotPresent() {
        //Given
        Long id = -1L;

        //When
        var actual = movieRepository.existsMovieByDirectorId(id);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void existsMovieByActorsContainingId() {
        //Given
        String title = "Interstellar";
        Actor actor = new Actor("Cilian", "Murphy");
        Director director = new Director();

        actorRepository.save(actor);
        directorRepository.save(director);

        Long id = actorRepository.findAll()
                .stream()
                .filter(a -> a.getFirstName().equals("Cilian") && a.getLastName().equals("Murphy"))
                .map(Actor::getId)
                .findFirst()
                .orElseThrow();

        Movie movie = new Movie(title, director, List.of(actor));

        movieRepository.save(movie);

        //When
        var actual = movieRepository.existsMovieByActorsContainingId(id);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsMovieByActorsContainingIdFailWhenActorNotPresent() {
        //Given
        Long id = -1L;

        //When
        var actual = movieRepository.existsMovieByActorsContainingId(id);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void existsMovieByCategoriesContainingId() {
        //Given
        String title = "Interstellar";

        Category category = new Category("Sci-Fi", null);
        categoryRepository.save(category);

        Long id = categoryRepository.findAll()
                .stream()
                .filter(a -> category.getCategoryName().equals("Sci-Fi"))
                .map(Category::getId)
                .findFirst()
                .orElseThrow();

        Movie movie = new Movie(title, List.of(category));

        movieRepository.save(movie);

        //When
        var actual = movieRepository.existsMovieByCategoriesContainingId(id);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsMovieByCategoriesContainingIdFailsWhenCategoryNotPresent() {
        //Given
        Long id = -1L;

        //When
        var actual = movieRepository.existsMovieByCategoriesContainingId(id);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void findAllByTitleContaining() {
        // Given
        String substring = "In";
        Movie movie1 = new Movie(1L, "Interstellar");
        Movie movie2 = new Movie(2L, "Inception");
        movieRepository.save(movie1);
        movieRepository.save(movie2);

        //When
        var actualMoviesOptionalList = movieRepository.findAllByTitleContaining(substring);

        assertThat(actualMoviesOptionalList).isPresent();

        var actualMoviesList = actualMoviesOptionalList.get();

        //Then
        assertThat(actualMoviesList).isNotEmpty();
        assertThat(actualMoviesList).hasSize(2);
    }

    @Test
    void findAllByRatingGreaterThanEqual() {
        // Given
        Movie movie1 = new Movie(1L, "Interstellar");
        movie1.setRating(8.5); // Assuming Interstellar has a rating of 8.5
        Movie movie2 = new Movie(2L, "Inception");
        movie2.setRating(9.0); // Assuming Inception has a rating of 9.0
        movieRepository.save(movie1);
        movieRepository.save(movie2);

        // When
        var actualMoviesOptionalList = movieRepository.findAllByRatingGreaterThanEqual(8.5); // Movies with a rating of 8.5 or higher

        // Then
        assertThat(actualMoviesOptionalList).isPresent();

        var actualMoviesList = actualMoviesOptionalList.get();

        assertThat(actualMoviesList).isNotEmpty();
        assertThat(actualMoviesList).hasSize(2); // Both movies should match the condition
    }

    @Test
    void findAllByDirectorId() {
        // Given
        Director director1 = new Director("Christopher", "Nolan");
        directorRepository.save(director1);

        Movie movie1 = new Movie(1L, "Interstellar");
        movie1.setDirector(director1);
        Movie movie2 = new Movie(2L, "Inception");
        movie2.setDirector(director1);
        movieRepository.save(movie1);
        movieRepository.save(movie2);

        // When
        var actualMoviesOptionalList = movieRepository.findAllByDirectorId(director1.getId());

        // Then
        assertThat(actualMoviesOptionalList).isPresent();

        var actualMoviesList = actualMoviesOptionalList.get();

        assertThat(actualMoviesList).isNotEmpty();
        assertThat(actualMoviesList).hasSize(2); // Both movies should have the same director
    }

    @Test
    void findAllByCategoriesId() {
        // Given
        Category sciFiCategory = new Category("Sci-Fi", new ArrayList<>());
        categoryRepository.save(sciFiCategory);

        Movie movie1 = new Movie(1L, "Interstellar");
        Movie movie2 = new Movie(2L, "Inception");
        movie1.setCategories(List.of(sciFiCategory));
        movie2.setCategories(List.of(sciFiCategory));
        movieRepository.save(movie1);
        movieRepository.save(movie2);

        // When
        var actualMoviesOptionalList = movieRepository.findAllByCategoriesId(sciFiCategory.getId());

        // Then
        assertThat(actualMoviesOptionalList).isPresent();

        var actualMoviesList = actualMoviesOptionalList.get();

        assertThat(actualMoviesList).isNotEmpty();
        assertThat(actualMoviesList).hasSize(2); // Both movies should belong to the same category
    }

    @Test
    void findAllByActorsId() {
        // Given
        Actor actor1 = new Actor("Leonardo", "DiCaprio");
        actorRepository.save(actor1);

        Movie movie1 = new Movie(1L, "Interstellar");
        Movie movie2 = new Movie(2L, "Inception");
        movie1.setActors(List.of(actor1));
        movie2.setActors(List.of(actor1));
        movieRepository.save(movie1);
        movieRepository.save(movie2);

        // When
        var actualMoviesOptionalList = movieRepository.findAllByActorsId(actor1.getId());

        // Then
        assertThat(actualMoviesOptionalList).isPresent();

        var actualMoviesList = actualMoviesOptionalList.get();

        assertThat(actualMoviesList).isNotEmpty();
        assertThat(actualMoviesList).hasSize(2); // Both movies should have the same actor
    }
}