package pl.patrykjava.cinemate.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    boolean existsMovieByTitle(String title);

    @Query("SELECT COUNT(m) > 0 FROM Movie m WHERE m.director.id = :directorId")
    boolean existsMovieByDirectorId(Long directorId);

    @Query("SELECT COUNT(m) > 0 FROM Movie m JOIN m.actors a WHERE a.id = :actorId")
    boolean existsMovieByActorsContainingId(Long actorId);

    @Query("SELECT COUNT(m) > 0 FROM Movie m JOIN m.categories c WHERE c.id = :categoryId")
    boolean existsMovieByCategoriesContainingId(Long categoryId);

    @Query("SELECT COUNT(m) > 0 FROM Movie m WHERE m.title = :title AND m.director.id = :directorId")
    boolean existsMovieByTitleAndDirectorId(String title, Long directorId);


    Optional<List<Movie>> findAllByTitleContaining(String keyword);

    Optional<List<Movie>> findAllByRatingGreaterThanEqual(Double rating);

    Optional<List<Movie>> findAllByDirectorId(Long id);
    Optional<List<Movie>> findAllByCategoriesId(Long id);
    Optional<List<Movie>> findAllByActorsId(Long id);
}
