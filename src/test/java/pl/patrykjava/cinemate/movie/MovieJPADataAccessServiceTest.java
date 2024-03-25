package pl.patrykjava.cinemate.movie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MovieJPADataAccessServiceTest {

    private MovieJPADataAccessService movieJPADataAccessService;
    @Mock
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp() {
        movieJPADataAccessService = new MovieJPADataAccessService(movieRepository);
    }

    @Test
    void selectMovieById() {
        //Given
        Long id = 1L;

        //When
        movieJPADataAccessService.selectMovieById(id);

        //Then
        verify(movieRepository).findById(id);
    }

    @Test
    void selectAllMoviesByCategoryId() {
        //Given
        Long id = 1L;

        //When
        movieJPADataAccessService.selectAllMoviesByCategoryId(id);

        //Then
        verify(movieRepository).findAllByCategoriesId(id);
    }

    @Test
    void selectAllMoviesByActorId() {
        //Given
        Long id = 1L;

        //When
        movieJPADataAccessService.selectAllMoviesByActorId(id);

        //Then
        verify(movieRepository).findAllByActorsId(id);
    }

    @Test
    void selectAllMoviesByDirectorId() {
        //Given
        Long id = 1L;

        //When
        movieJPADataAccessService.selectAllMoviesByDirectorId(id);

        //Then
        verify(movieRepository).findAllByDirectorId(id);
    }

    @Test
    void selectAllMovies() {
        //When
        movieJPADataAccessService.selectAllMovies();

        //Then
        verify(movieRepository).findAll();
    }

    @Test
    void existsMovieWithId() {
        //Given
        Long id = 1L;

        //When
        movieJPADataAccessService.existsMovieWithId(id);

        //Then
        verify(movieRepository).existsById(id);
    }

    @Test
    void existsMovieWithCategoryId() {
        //Given
        Long id = 1L;

        //When
        movieJPADataAccessService.existsMovieWithCategoryId(id);

        //Then
        verify(movieRepository).existsMovieByCategoriesContainingId(id);
    }

    @Test
    void existsMovieWithActorId() {
        //Given
        Long id = 1L;

        //When
        movieJPADataAccessService.existsMovieWithActorId(id);

        //Then
        verify(movieRepository).existsMovieByActorsContainingId(id);
    }

    @Test
    void existsMovieWithDirectorId() {
        //Given
        Long id = 1L;

        //When
        movieJPADataAccessService.existsMovieWithDirectorId(id);

        //Then
        verify(movieRepository).existsMovieByDirectorId(id);
    }

    @Test
    void insertMovie() {
        //Given
        Movie movie = new Movie();

        //When
        movieJPADataAccessService.insertMovie(movie);

        //Then
        verify(movieRepository).save(movie);
    }

    @Test
    void deleteMovieById() {
        //Given
        Long id = 1L;

        //When
        movieJPADataAccessService.deleteMovieById(id);

        //Then
        verify(movieRepository).deleteById(id);
    }

    @Test
    void updateMovie() {
        //Given
        Movie movie = new Movie();

        //When
        movieJPADataAccessService.updateMovie(movie);

        //Then
        verify(movieRepository).save(movie);
    }
}