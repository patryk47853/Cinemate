package pl.patrykjava.cinemate.actor;

import org.springframework.stereotype.Service;
import pl.patrykjava.cinemate.exception.DuplicateResourceException;
import pl.patrykjava.cinemate.exception.RequestValidationException;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;
import pl.patrykjava.cinemate.movie.Movie;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {

    private final ActorDao actorDao;

    public ActorService(ActorDao actorDao) {
        this.actorDao = actorDao;
    }

    public Actor getActorById(Long id) {
        return actorDao.selectActorById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No actor with ID: " + id + " has been found."));
    }

    public List<Actor> getActorsByLastName(String lastName) {
        return actorDao.selectActorsByLastName(lastName)
                .orElseThrow(() -> new ResourceNotFoundException("No actor with last name: " + lastName + " has been found."));
    }

    public List<Actor> findAllActors() {
        return actorDao.selectAllActors();
    }

    public void addActor(ActorAddRequest request) {
        String firstName = request.firstName();
        String lastName = request.lastName();

        if (actorDao.existsActorWithFullName(firstName, lastName)) {
            throw new DuplicateResourceException("Actor: " + firstName + " " + lastName + " already exists in database.");
        }

        Actor actor = new Actor(firstName, lastName);

        actorDao.insertActor(actor);
    }

    public Actor findOrCreateActor(String firstName, String lastName) {
        Optional<Actor> actual = actorDao.selectActorByFullName(firstName, lastName);
        Actor actor = null;

        if (actual.isEmpty()) {
            actor = new Actor(firstName, lastName);
            actorDao.insertActor(actor);
        }

        return actor;
    }

    public void deleteActorById(Long id) {
        if (!actorDao.existsActorWithId(id))
            throw new ResourceNotFoundException("Actor with ID: " + id + " not found.");

        actorDao.deleteActorById(id);
    }

    public void updateActor(Long id, ActorUpdateRequest request) {
        Actor actor = actorDao.selectActorById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No actor with such ID: " + id + " has been found."));

        boolean changed = false;

        if (request.firstName() != null && !request.firstName().equals(actor.getFirstName())) {
            checkAndUpdateActorFullName(request.firstName(), actor.getLastName(), actorDao);
            actor.setFirstName(request.firstName());
            changed = true;
        }

        if (request.lastName() != null && !request.lastName().equals(actor.getLastName())) {
            checkAndUpdateActorFullName(actor.getFirstName(), request.lastName(), actorDao);
            actor.setLastName(request.lastName());
            changed = true;
        }

        if (request.moviesToAdd() != null) {
            List<Movie> mainMovies = actor.getMovies();
            List<Movie> moviesToAdd = request.moviesToAdd();

            for (Movie movieToAdd : moviesToAdd) {
                boolean alreadyExists = isMovieAlreadyAssignedToActor(mainMovies, movieToAdd);
                if (alreadyExists) {
                    throw new DuplicateResourceException("Movie " + movieToAdd.getTitle() +
                            " is already assigned to this actor.");
                }
                mainMovies.add(movieToAdd);
            }

            actor.setMovies(mainMovies);
            changed = true;
        }

        if (!changed) {
            throw new RequestValidationException("No changes were made.");
        }

        actorDao.updateActor(actor);
    }

    private static boolean isMovieAlreadyAssignedToActor(List<Movie> mainMovies, Movie movieToAdd) {
        return mainMovies.stream()
                .anyMatch(existingMovie -> existingMovie.getTitle().equals(movieToAdd.getTitle()) &&
                        existingMovie.getActors().stream().anyMatch(a ->
                                a.getFirstName().equals(movieToAdd.getDirector().getFirstName()) &&
                                        a.getLastName().equals(movieToAdd.getDirector().getLastName())
                        )
                );
    }

    private void checkAndUpdateActorFullName(String firstName, String lastName, ActorDao actorDao) {
        if (actorDao.existsActorWithFullName(firstName, lastName)) {
            throw new DuplicateResourceException("Actor: " + firstName + " " + lastName + " already exists in database.");
        }
    }
}
