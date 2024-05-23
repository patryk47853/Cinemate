package pl.patrykjava.cinemate.movie;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{movieId}")
    public Movie getMovie(@PathVariable("movieId") Long movieId) {
        return movieService.getMovie(movieId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/actor/{actorId}")
    public List<Movie> getAllMoviesByActorId(@PathVariable("actorId") Long actorId) {
        return movieService.getAllMoviesByActorId(actorId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/director/{directorId}")
    public List<Movie> getAllMoviesByDirectorId(@PathVariable("directorId") Long directorId) {
        return movieService.getAllMoviesByDirectorId(directorId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/category/{categoryId}")
    public List<Movie> getAllMoviesByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return movieService.getAllMoviesByCategoryId(categoryId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> addMovie(@RequestBody MovieAddRequest request) {
        movieService.addMovie(request);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{movieId}")
    public void deleteMovie(@PathVariable("movieId") Long movieId) {
        movieService.deleteMovieById(movieId);
    }
}