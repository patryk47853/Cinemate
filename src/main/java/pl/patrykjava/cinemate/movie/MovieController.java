package pl.patrykjava.cinemate.movie;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{movieId}")
    public Movie getMovie(@PathVariable("movieId") Long movieId) {
        return movieService.getMovie(movieId);
    }

    @GetMapping("/all")
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/actor/{actorId}")
    public List<Movie> getAllMoviesByActorId(@PathVariable("actorId") Long actorId) {
        return movieService.getAllMoviesByActorId(actorId);
    }

    @GetMapping("/director/{directorId}")
    public List<Movie> getAllMoviesByDirectorId(@PathVariable("directorId") Long directorId) {
        return movieService.getAllMoviesByDirectorId(directorId);
    }

    @GetMapping("/category/{categoryId}")
    public List<Movie> getAllMoviesByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return movieService.getAllMoviesByCategoryId(categoryId);
    }

    @PostMapping
    public ResponseEntity<?> addMovie(@RequestBody MovieAddRequest request) {
        movieService.addMovie(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{movieId}")
    public void deleteMovie(@PathVariable("movieId") Long movieId) {
        movieService.deleteMovieById(movieId);
    }
}