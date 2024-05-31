package pl.patrykjava.cinemate.movie;

import org.springframework.stereotype.Service;
import pl.patrykjava.cinemate.actor.Actor;
import pl.patrykjava.cinemate.actor.ActorService;
import pl.patrykjava.cinemate.category.Category;
import pl.patrykjava.cinemate.category.CategoryService;
import pl.patrykjava.cinemate.director.Director;
import pl.patrykjava.cinemate.director.DirectorService;
import pl.patrykjava.cinemate.exception.DuplicateResourceException;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private final MovieDao movieDao;
    private final ActorService actorService;
    private final CategoryService categoryService;

    private final DirectorService directorService;

    public MovieService(MovieDao movieDao, ActorService actorService, CategoryService categoryService, DirectorService directorService) {
        this.movieDao = movieDao;
        this.actorService = actorService;
        this.categoryService = categoryService;
        this.directorService = directorService;
    }

    public Movie getMovie(Long id) {
        return movieDao.selectMovieById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No movie with ID: " + id + " has been found."));
    }

    public List<Movie> getAllMovies() {
        return movieDao.selectAllMovies();
    }

    public List<Movie> getAllMoviesByCategoryId(Long id) {
        if (!movieDao.existsMovieWithCategoryId(id)) {
            throw new ResourceNotFoundException("No movie with category ID: " + id + " has been found.");
        }
        return movieDao.selectAllMoviesByCategoryId(id).orElseThrow(() -> new ResourceNotFoundException("No movies found for category ID: " + id));
    }

    public List<Movie> getAllMoviesByActorId(Long id) {
        if (!movieDao.existsMovieWithActorId(id)) {
            throw new ResourceNotFoundException("No movie with actor ID: " + id + " has been found.");
        }
        return movieDao.selectAllMoviesByActorId(id).orElseThrow(() -> new ResourceNotFoundException("No movies found for actor ID: " + id));
    }

    public List<Movie> getAllMoviesByDirectorId(Long id) {
        if (!movieDao.existsMovieWithDirectorId(id)) {
            throw new ResourceNotFoundException("No movie with director ID: " + id + " has been found.");
        }
        return movieDao.selectAllMoviesByDirectorId(id).orElseThrow(() -> new ResourceNotFoundException("No movies found for director ID: " + id));
    }

    public void addMovie(MovieAddRequest movieAddRequest) {
        Director director = parseAndRetrieveDirector(movieAddRequest.director());

        if (movieDao.existsMovieWithTitleAndDirectorId(movieAddRequest.title(), director.getId())) {
            throw new DuplicateResourceException("A movie with the same title and director already exists.");
        }

        Movie movie = new Movie(
                movieAddRequest.title(),
                movieAddRequest.rating(),
                movieAddRequest.description(),
                movieAddRequest.imgUrl(),
                movieAddRequest.awards(),
                parseAndRetrieveCategories(movieAddRequest.categories()),
                director,
                parseAndRetrieveActors(movieAddRequest.actors()),
                new ArrayList<>(),
                new ArrayList<>()
        );

        movieDao.insertMovie(movie);
    }

    public void deleteMovieById(Long id) {
        if (!movieDao.existsMovieWithId(id))
            throw new ResourceNotFoundException("Movie with ID: " + id + " not found.");

        movieDao.deleteMovieById(id);
    }

    private List<Category> parseAndRetrieveCategories(String categoriesJson) {
        String[] categoriesName = categoriesJson.split(", ");
        List<Category> categories = new ArrayList<>();

        for (String categoryName : categoriesName) {
            Category category = categoryService.findOrCreateCategory(categoryName);
            categories.add(category);
        }

        return categories;
    }

    private Director parseAndRetrieveDirector(String directorJson) {
        String[] directorName = directorJson.split(" ");

        return directorService.findOrCreateDirector(directorName[0], directorName[1]);
    }

    private List<Actor> parseAndRetrieveActors(String actorsJson) {
        String[] actorNames = actorsJson.split(", ");
        List<Actor> actors = new ArrayList<>();

        for (String actorName : actorNames) {
            String[] names = actorName.trim().split(" ");
            if (names.length == 2) {
                String firstName = names[0];
                String lastName = names[1];
                Actor actor = actorService.findOrCreateActor(firstName, lastName);
                actors.add(actor);
            } else if (names.length == 3) {
                String firstAndSecondName = names[0] + " " + names[1];
                String lastName = names[2];
                Actor actor = actorService.findOrCreateActor(firstAndSecondName, lastName);
                actors.add(actor);
            }
        }

        return actors;
    }
}
