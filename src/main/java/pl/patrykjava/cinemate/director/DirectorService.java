package pl.patrykjava.cinemate.director;

import org.springframework.stereotype.Service;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class DirectorService {

    private final DirectorRepository directorRepository;

    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public Director findDirectorById(Long id) {
        return directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No actor with ID: " + id + " has been found."));
    }

    public List<Director> findAllDirectors() {
        return directorRepository.findAll();
    }
}
