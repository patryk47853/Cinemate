package pl.patrykjava.cinemate.director;

import org.springframework.stereotype.Service;
import pl.patrykjava.cinemate.exception.DuplicateResourceException;
import pl.patrykjava.cinemate.exception.RequestValidationException;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;
import pl.patrykjava.cinemate.movie.Movie;

import java.util.List;

@Service
public class DirectorService {

    private final DirectorDao directorDao;

    public DirectorService(DirectorDao directorDao) {
        this.directorDao = directorDao;
    }

    public Director getDirectorById(Long id) {
        return directorDao.selectDirectorById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No director with ID: " + id + " has been found."));
    }

    public List<Director> getDirectorsByLastName(String lastName) {
        return directorDao.selectDirectorsByLastName(lastName)
                .orElseThrow(() -> new ResourceNotFoundException("No director with last name: " + lastName + " has been found."));
    }

    public List<Director> findAllDirectors() {
        return directorDao.selectAllDirectors();
    }

    public void addDirector(DirectorAddRequest request) {
        String firstName = request.firstName();
        String lastName = request.lastName();

        if (directorDao.existsDirectorWithFullName(firstName, lastName)) {
            String fullName = firstName + " " + lastName;
            throw new DuplicateResourceException("Director: " + fullName + " already exists.");
        }

        Director director = new Director(firstName, lastName);

        directorDao.insertDirector(director);
    }

    public void deleteDirectorById(Long id) {
        if (!directorDao.existsDirectorWithId(id))
            throw new ResourceNotFoundException("Director with ID: " + id + " not found.");

        directorDao.deleteDirectorById(id);
    }

    public void updateDirector(Long id, DirectorUpdateRequest request) {
        Director director = directorDao.selectDirectorById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No director with such ID: " + id + " has been found."));

        boolean changed = false;

        if (request.firstName() != null && !request.firstName().equals(director.getFirstName())) {
            checkAndUpdateDirectorFullName(request.firstName(), director.getLastName(), directorDao);
            director.setFirstName(request.firstName());
            changed = true;
        }

        if (request.lastName() != null && !request.lastName().equals(director.getLastName())) {
            checkAndUpdateDirectorFullName(director.getFirstName(), request.lastName(), directorDao);
            director.setLastName(request.lastName());
            changed = true;
        }

        if (request.moviesToAdd() != null) {
            List<Movie> mainMovies = director.getMovies();
            List<Movie> moviesToAdd = request.moviesToAdd();

            for (Movie movieToAdd : moviesToAdd) {
                if (mainMovies.contains(movieToAdd)) {
                    throw new DuplicateResourceException("Movie " + movieToAdd.getTitle() +
                              " is already assigned to this director.");
                }
                mainMovies.add(movieToAdd);
            }

            director.setMovies(mainMovies);
            changed = true;
        }

        if (!changed) {
            throw new RequestValidationException("No changes were made.");
        }

        directorDao.updateDirector(director);
    }

    private void checkAndUpdateDirectorFullName(String firstName, String lastName, DirectorDao directorDao) {
        if (directorDao.existsDirectorWithFullName(firstName, lastName)) {
            throw new DuplicateResourceException("Director: " + firstName + " " + lastName + " already exists.");
        }
    }
}
