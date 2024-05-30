package pl.patrykjava.cinemate.director;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DirectorJPADataAccessService implements DirectorDao {
    private final DirectorRepository directorRepository;

    public DirectorJPADataAccessService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @Override
    public Optional<Director> selectDirectorById(Long id) {
        return directorRepository.findById(id);
    }

    @Override
    public Optional<Director> selectDirectorByFullName(String firstName, String lastName) {
        return directorRepository.findDirectorByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Optional<List<Director>> selectDirectorsByLastName(String lastName) {
        return directorRepository.findDirectorsByLastName(lastName);
    }

    @Override
    public boolean existsDirectorWithLastName(String lastName) {
        return directorRepository.existsDirectorByLastName(lastName);
    }

    @Override
    public boolean existsDirectorWithFullName(String firstName, String lastName) {
        return directorRepository.existsDirectorByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public boolean existsDirectorWithId(Long id) {
        return directorRepository.existsDirectorById(id);
    }

    @Override
    public List<Director> selectAllDirectors() {
        return directorRepository.findAll();
    }

    @Override
    public void insertDirector(Director director) {
        directorRepository.save(director);
    }

    @Override
    public void deleteDirectorById(Long id) {
        directorRepository.deleteById(id);
    }

    @Override
    public void updateDirector(Director director) {
        directorRepository.save(director);
    }
}
