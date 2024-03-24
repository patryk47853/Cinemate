package pl.patrykjava.cinemate.movie;

import org.springframework.stereotype.Service;
import pl.patrykjava.cinemate.exception.DuplicateResourceException;
import pl.patrykjava.cinemate.exception.RequestValidationException;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieDao movieDao;

    public MovieService(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public Movie getMovie(Long id) {
        return movieDao.selectMovieById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No movie with ID: " + id + " has been found."));
    }

    public List<Movie> getAllMovies() {
        return movieDao.selectAllMovies();
    }

    public Optional<List<Movie>> getAllMoviesByCategoryId(Long id) {
        if(!movieDao.existsMovieWithCategoryId(id)) throw  new ResourceNotFoundException("No movie with category ID: " + id + " has been found.");

        return movieDao.selectAllMoviesByCategoryId(id);
    }

    public Optional<List<Movie>> getAllMoviesByActorId(Long id) {
        if(!movieDao.existsMovieWithActorId(id)) throw  new ResourceNotFoundException("No movie with actor ID: " + id + " has been found.");

        return movieDao.selectAllMoviesByActorId(id);
    }

    public Optional<List<Movie>> getAllMoviesByDirectorId(Long id) {
        if(!movieDao.existsMovieWithDirectorId(id)) throw  new ResourceNotFoundException("No movie with director ID: " + id + " has been found.");

        return movieDao.selectAllMoviesByDirectorId(id);
    }

    public void addMovie(MovieAddRequest movieAddRequest) {
        if (movieDao.existsMovieWithTitleAndDirectorId(movieAddRequest.title(), movieAddRequest.director().getId())) {
            throw new DuplicateResourceException("A movie with the same title and director already exists.");
        }

        Movie movie = new Movie(
                movieAddRequest.title(),
                movieAddRequest.rating(),
                movieAddRequest.description(),
                movieAddRequest.imgUrl(),
                movieAddRequest.categories(),
                movieAddRequest.director(),
                movieAddRequest.actors(),
                new ArrayList<>(),
                new ArrayList<>()
        );

        movieDao.insertMovie(movie);
    }


    public void deleteMovieById(Long id) {
        if (!movieDao.existsMovieWithId(id)) throw new ResourceNotFoundException("Movie with ID: " + id + " not found.");

        movieDao.deleteMovieById(id);
    }
}
