package pl.patrykjava.cinemate.movie;

import java.util.List;
import java.util.Optional;

public interface MovieDao {
    Optional<Movie> selectMovieById(Long id);
    Optional<List<Movie>> selectAllMoviesByCategoryId(Long id);
    Optional<List<Movie>> selectAllMoviesByActorId(Long id);
    Optional<List<Movie>> selectAllMoviesByDirectorId(Long id);
    List<Movie> selectAllMovies();
    boolean existsMovieWithId(Long id);
    boolean existsMovieWithCategoryId(Long id);
    boolean existsMovieWithActorId(Long id);
    boolean existsMovieWithDirectorId(Long id);
    boolean existsMovieWithTitleAndDirectorId(String title, Long id);
    void insertMovie(Movie movie);
    void deleteMovieById(Long id);
    void updateMovie(Movie movie);
}
