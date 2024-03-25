package pl.patrykjava.cinemate.movie;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MovieJPADataAccessService implements MovieDao {

    private final MovieRepository movieRepository;

    public MovieJPADataAccessService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Optional<Movie> selectMovieById(Long id) {
        return movieRepository.findById(id);
    }

    @Override
    public Optional<List<Movie>> selectAllMoviesByCategoryId(Long id) {
        return movieRepository.findAllByCategoriesId(id);
    }

    @Override
    public Optional<List<Movie>> selectAllMoviesByActorId(Long id) {
        return movieRepository.findAllByActorsId(id);
    }

    @Override
    public Optional<List<Movie>> selectAllMoviesByDirectorId(Long id) {
        return movieRepository.findAllByDirectorId(id);
    }

    @Override
    public List<Movie> selectAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public boolean existsMovieWithId(Long id) {
        return movieRepository.existsById(id);
    }

    @Override
    public boolean existsMovieWithCategoryId(Long id) {
        return movieRepository.existsMovieByCategoriesContainingId(id);
    }

    @Override
    public boolean existsMovieWithActorId(Long id) {
        return movieRepository.existsMovieByActorsContainingId(id);
    }

    @Override
    public boolean existsMovieWithDirectorId(Long id) {
        return movieRepository.existsMovieByDirectorId(id);
    }

    @Override
    public boolean existsMovieWithTitleAndDirectorId(String title, Long id) {
        return movieRepository.existsMovieByTitleAndDirectorId(title, id);
    }

    @Override
    public void insertMovie(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public void deleteMovieById(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public void updateMovie(Movie movie) {
        movieRepository.save(movie);
    }
}
