package pl.patrykjava.cinemate.movie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.patrykjava.cinemate.director.Director;
import pl.patrykjava.cinemate.exception.DuplicateResourceException;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    private MovieService movieService;

    @Mock
    private MovieDao movieDao;

    @BeforeEach
    void setUp() {
        movieService = new MovieService(movieDao);
    }

    @Test
    void canGetMovie() {
        //Given
        Long id = 1L;

        Movie movie = new Movie(
                1L,"Inception", 8.5, "A great movie", "inception.jpg",
                new ArrayList<>(), new Director(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
        );

        when(movieDao.selectMovieById(id)).thenReturn(Optional.of(movie));

        //When
        Movie actual = movieService.getMovie(id);

        //Then
        assertThat(actual).isEqualTo(movie);
    }

    @Test
    void willThrowWhenGetMovieReturnsEmptyOptional() {
        //Given
        Long id = 1L;

        when(movieDao.selectMovieById(id)).thenReturn(Optional.empty());

        //Then
        assertThatThrownBy(() -> movieService.getMovie(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No movie with ID: " + id + " has been found.");
    }

    @Test
    void getAllMovies() {
        //When
        movieService.getAllMovies();

        //Then
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
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        verify(movieDao).selectAllMoviesByCategoryId(categoryId);
    }


    @Test
    void willThrowWhenNoMoviesWithCategoryId() {
        //Given
        Long categoryId = 1L;

        when(movieDao.existsMovieWithCategoryId(categoryId)).thenReturn(false);

        //Then
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
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        verify(movieDao).selectAllMoviesByActorId(actorId);
    }

    @Test
    void willThrowWhenNoMoviesWithActorId() {
        //Given
        Long actorId = 1L;

        when(movieDao.existsMovieWithActorId(actorId)).thenReturn(false);

        //Then
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
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        verify(movieDao).selectAllMoviesByDirectorId(directorId);
    }

    @Test
    void willThrowWhenNoMoviesWithDirectorId() {
        //Given
        Long directorId = 1L;

        when(movieDao.existsMovieWithDirectorId(directorId)).thenReturn(false);

        //Then
        assertThatThrownBy(() -> movieService.getAllMoviesByDirectorId(directorId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No movie with director ID: " + directorId + " has been found.");
    }

    @Test
    void addMovie() {
        //Given
        String title = "Inception";
        Long directorId = 1L;

        Director director = new Director(directorId, "Christopher", "Nolan", new ArrayList<>());

        when(movieDao.existsMovieWithTitleAndDirectorId(title, directorId)).thenReturn(false);

        MovieAddRequest request = new MovieAddRequest(
                title, 8.5, "A great movie", "inception.jpg",
                new ArrayList<>(), director, new ArrayList<>()
        );
        //When
        movieService.addMovie(request);

        //Then
        ArgumentCaptor<Movie> movieArgumentCaptor = ArgumentCaptor.forClass(Movie.class);

        verify(movieDao).insertMovie(movieArgumentCaptor.capture());

        Movie actual = movieArgumentCaptor.getValue();

        assertThat(actual.getId()).isNull();
        assertThat(actual.getTitle()).isEqualTo(request.title());
    }

    @Test
    void willThrowWhenAddMovieWithExistingTitleAndDirectorId() {
        //Given
        String title = "Inception";
        Long directorId = 1L;

        Director director = new Director(directorId, "Christopher", "Nolan", new ArrayList<>());

        when(movieDao.existsMovieWithTitleAndDirectorId(title, directorId)).thenReturn(true);

        MovieAddRequest request = new MovieAddRequest(
                title, 8.5, "A great movie", "inception.jpg",
                new ArrayList<>(), director, new ArrayList<>()
        );

        //When
        assertThatThrownBy(() -> movieService.addMovie(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("A movie with the same title and director already exists.");

        //Then
        verify(movieDao, never()).insertMovie(any());
    }

    @Test
    void deleteMovieById() {
        //Given
        Long id = 1L;

        when(movieDao.existsMovieWithId(id)).thenReturn(true);

        //When
        movieService.deleteMovieById(id);

        //Then
        verify(movieDao).deleteMovieById(id);
    }

    @Test
    void willThrowWhenDeleteMovieByIdDoesNotExists() {
        //Given
        Long id = 1L;

        when(movieDao.existsMovieWithId(id)).thenReturn(false);

        //When
        assertThatThrownBy(() -> movieService.deleteMovieById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Movie with ID: " + id + " not found.");

        //Then
        verify(movieDao, never()).deleteMovieById(any());
    }
}

