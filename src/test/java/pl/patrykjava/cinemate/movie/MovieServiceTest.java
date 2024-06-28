package pl.patrykjava.cinemate.movie;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.patrykjava.cinemate.actor.Actor;
import pl.patrykjava.cinemate.actor.ActorService;
import pl.patrykjava.cinemate.category.Category;
import pl.patrykjava.cinemate.category.CategoryService;
import pl.patrykjava.cinemate.director.Director;
import pl.patrykjava.cinemate.director.DirectorService;
import pl.patrykjava.cinemate.exception.DuplicateResourceException;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieDao movieDao;

    @Mock
    private ActorService actorService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private DirectorService directorService;

    @InjectMocks
    private MovieService movieService;

    @Test
    void canGetMovie() {
        // Given
        Long id = 1L;
        Movie movie = new Movie(1L, "Inception", 8.5, "A great movie", "inception.jpg", "1999", "Oscar",
                new ArrayList<>(), new Director(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        when(movieDao.selectMovieById(id)).thenReturn(Optional.of(movie));

        // When
        Movie actual = movieService.getMovie(id);

        // Then
        assertThat(actual).isEqualTo(movie);
    }

    @Test
    void willThrowWhenGetMovieReturnsEmptyOptional() {
        // Given
        Long id = 1L;

        when(movieDao.selectMovieById(id)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> movieService.getMovie(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No movie with ID: " + id + " has been found.");
    }

    @Test
    void getAllMovies() {
        // When
        movieService.getAllMovies();

        // Then
        verify(movieDao).selectAllMovies();
    }

    @Test
    void getAllMoviesByCategoryId() {
        // Given
        Long categoryId = 1L;
        List<Movie> movies = Arrays.asList(new Movie(), new Movie());

        when(movieDao.existsMovieWithCategoryId(categoryId)).thenReturn(true);
        when(movieDao.selectAllMoviesByCategoryId(categoryId)).thenReturn(Optional.of(movies));

        // When
        List<Movie> result = movieService.getAllMoviesByCategoryId(categoryId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        verify(movieDao).selectAllMoviesByCategoryId(categoryId);
    }

    @Test
    void willThrowWhenNoMoviesWithCategoryId() {
        // Given
        Long categoryId = 1L;

        when(movieDao.existsMovieWithCategoryId(categoryId)).thenReturn(false);

        // Then
        assertThatThrownBy(() -> movieService.getAllMoviesByCategoryId(categoryId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No movie with category ID: " + categoryId + " has been found.");
    }

    @Test
    void getAllMoviesByActorId() {
        // Given
        Long actorId = 1L;
        List<Movie> movies = Arrays.asList(new Movie(), new Movie());

        when(movieDao.existsMovieWithActorId(actorId)).thenReturn(true);
        when(movieDao.selectAllMoviesByActorId(actorId)).thenReturn(Optional.of(movies));

        // When
        List<Movie> result = movieService.getAllMoviesByActorId(actorId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        verify(movieDao).selectAllMoviesByActorId(actorId);
    }

    @Test
    void willThrowWhenNoMoviesWithActorId() {
        // Given
        Long actorId = 1L;

        when(movieDao.existsMovieWithActorId(actorId)).thenReturn(false);

        // Then
        assertThatThrownBy(() -> movieService.getAllMoviesByActorId(actorId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No movie with actor ID: " + actorId + " has been found.");
    }

    @Test
    void getAllMoviesByDirectorId() {
        // Given
        Long directorId = 1L;
        List<Movie> movies = Arrays.asList(new Movie(), new Movie());

        when(movieDao.existsMovieWithDirectorId(directorId)).thenReturn(true);
        when(movieDao.selectAllMoviesByDirectorId(directorId)).thenReturn(Optional.of(movies));

        // When
        List<Movie> result = movieService.getAllMoviesByDirectorId(directorId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        verify(movieDao).selectAllMoviesByDirectorId(directorId);
    }

    @Test
    void willThrowWhenNoMoviesWithDirectorId() {
        // Given
        Long directorId = 1L;

        when(movieDao.existsMovieWithDirectorId(directorId)).thenReturn(false);

        // Then
        assertThatThrownBy(() -> movieService.getAllMoviesByDirectorId(directorId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No movie with director ID: " + directorId + " has been found.");
    }

    @Test
    void addMovie() {
        // Given
        String categories = "Action, Sci-Fi";
        String directorFullName = "Christopher Nolan";
        String actors = "Leonardo DiCaprio, Joseph Gordon-Levitt";

        String title = "Inception";
        Double rating = 8.5;
        String description = "A great movie";
        String imgUrl = "inception.jpg";
        String year = "1999";
        String awards = "Oscar";

        // Mocked director and actors
        Director director = new Director(1L, "Christopher", "Nolan", new ArrayList<>());
        Actor actor1 = new Actor(1L, "Leonardo", "DiCaprio", new ArrayList<>());
        Actor actor2 = new Actor(2L, "Joseph", "Gordon-Levitt", new ArrayList<>());

        List<Category> parsedCategories = Arrays.asList(new Category(1L, "Action", new ArrayList<>()), new Category(2L, "Sci-Fi", new ArrayList<>()));

        when(directorService.findOrCreateDirector("Christopher", "Nolan")).thenReturn(director);
        when(actorService.findOrCreateActor("Leonardo", "DiCaprio")).thenReturn(actor1);
        when(actorService.findOrCreateActor("Joseph", "Gordon-Levitt")).thenReturn(actor2);
        when(categoryService.findOrCreateCategory("Action")).thenReturn(parsedCategories.get(0));
        when(categoryService.findOrCreateCategory("Sci-Fi")).thenReturn(parsedCategories.get(1));
        when(movieDao.existsMovieWithTitleAndDirectorId(title, director.getId())).thenReturn(false);

        MovieAddRequest request = new MovieAddRequest(
                title, rating, description, imgUrl, awards, year, categories, directorFullName, actors
        );

        // When
        movieService.addMovie(request);

        // Then
        ArgumentCaptor<Movie> movieArgumentCaptor = ArgumentCaptor.forClass(Movie.class);
        verify(movieDao).insertMovie(movieArgumentCaptor.capture());
        Movie actual = movieArgumentCaptor.getValue();

        assertThat(actual.getId()).isNull();
        assertThat(actual.getTitle()).isEqualTo(request.title());
        assertThat(actual.getDirector()).isEqualTo(director);
        assertThat(actual.getActors()).containsExactlyInAnyOrder(actor1, actor2);
        assertThat(actual.getCategories()).containsExactlyInAnyOrderElementsOf(parsedCategories);
        assertThat(actual.getAwards()).isEqualTo(request.awards());
        assertThat(actual.getRating()).isEqualTo(request.rating());
        assertThat(actual.getDescription()).isEqualTo(request.description());
        assertThat(actual.getImgUrl()).isEqualTo(request.imgUrl());
    }

    @Test
    void willThrowWhenAddMovieWithExistingTitleAndDirectorId() {
        // Given
        String title = "Inception";
        String directorFullName = "Christopher Nolan";

        Director director = new Director(1L, "Christopher", "Nolan", new ArrayList<>());

        when(directorService.findOrCreateDirector("Christopher", "Nolan")).thenReturn(director);
        when(movieDao.existsMovieWithTitleAndDirectorId(title, director.getId())).thenReturn(true);

        MovieAddRequest request = new MovieAddRequest(
                title, 8.5, "A great movie", "inception.jpg", "Oscar", "1999", "Action, Sci-Fi", directorFullName, "Leonardo DiCaprio, Joseph Gordon-Levitt"
        );

        // When
        assertThatThrownBy(() -> movieService.addMovie(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("A movie with the same title and director already exists.");

        // Then
        verify(movieDao, never()).insertMovie(any());
    }

    @Test
    void deleteMovieById() {
        // Given
        Long id = 1L;

        when(movieDao.existsMovieWithId(id)).thenReturn(true);

        // When
        movieService.deleteMovieById(id);

        // Then
        verify(movieDao).deleteMovieById(id);
    }

    @Test
    void willThrowWhenDeleteMovieByIdDoesNotExists() {
        // Given
        Long id = 1L;

        when(movieDao.existsMovieWithId(id)).thenReturn(false);

        // When
        assertThatThrownBy(() -> movieService.deleteMovieById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Movie with ID: " + id + " not found.");

        // Then
        verify(movieDao, never()).deleteMovieById(any());
    }
}
